package com.koleychik.feature_documents.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.koleychik.feature_documents.R
import com.koleychik.feature_documents.databinding.FragmentDocumentsBinding
import com.koleychik.feature_documents.di.DocumentsFeatureComponentHolder
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModel
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentType
import javax.inject.Inject

class DocumentsFragment : Fragment() {

    private var _binding: FragmentDocumentsBinding? = null

    private val binding get() = _binding!!

    private val mapTypes: SparseArray<DocumentType> by lazy { createMap() }

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var searchingUIApi: SearchingUIApi

    @Inject
    internal lateinit var adapterApi: RvFilesAdapterApi

    @Inject
    internal lateinit var viewModelFactory: DocumentsViewModelFactory


    private val viewModel: DocumentsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DocumentsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentDocumentsBinding.inflate(layoutInflater, container, false)
        DocumentsFeatureComponentHolder.getComponent().inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewStub()
        setupSearching()
        createOnClickListener()
        createRv()
        createSwipeToRefresh()
        subscribe()
    }

    private fun subscribe() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (binding.carcass.swipeToRefresh.isRefreshing) return@observe

            if (isLoading) startLoading()
            else stopLoading()
        }
        viewModel.currentList.observe(viewLifecycleOwner, {
            resetViews()
            when {
                it == null -> viewModel.getFirstTimeDocumentModelsData(getTextFromEdtSearching())
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
        viewModel.listSelectFormats.observe(viewLifecycleOwner, {
            viewModel.setFullList(getTextFromEdtSearching())
        })
    }

    private fun createOnClickListener() {
        val onClickListener = View.OnClickListener {
            viewModel.listSelectFormats.value.run {
                val type = mapTypes[it.id] ?: return@run

                if (this!!.contains(type)) {
                    (it as TextView).setBackgroundResource(R.drawable.bg_type_document_dont_selected)
                    viewModel.listSelectFormats.value?.remove(type)
                } else {
                    (it as TextView).setBackgroundResource(R.drawable.bg_type_document)
                    viewModel.listSelectFormats.value?.add(type)
                }
            }
        }

        with(binding) {
            typePpt.setOnClickListener(onClickListener)
            typeDoc.setOnClickListener(onClickListener)
            typeDocx.setOnClickListener(onClickListener)
            typeB2.setOnClickListener(onClickListener)
            typePdf.setOnClickListener(onClickListener)
            typeEpub.setOnClickListener(onClickListener)
            typeTxt.setOnClickListener(onClickListener)
            typeOther.setOnClickListener(onClickListener)
        }

    }

    private fun startLoading() {
        resetViews()
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
    }

    private fun stopLoading() {
        loadingApi.run {
            setVisible(false)
            endAnimation()
        }
    }

    private fun showList(list: List<FileCarcass>) {
        adapterApi.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun resetViews() {
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun createSwipeToRefresh() {
        with(binding.carcass.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getDocuments(getTextFromEdtSearching())
            }
        }
    }

    private fun createRv() {
        val itemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayout.VERTICAL
        )
        binding.carcass.rv.apply {
            adapter = adapterApi
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(itemDecoration)
        }
        adapterApi.onClick = { model, _ ->
            viewModel.openFile(model)
        }
    }

    private fun setupViewStub() {
        loadingApi.setRootView(requireView())
        binding.carcass.viewStub.apply {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
    }

    private fun setupSearching() {
        searchingUIApi.run {
            setOnCloseSearching {
                binding.searchingInclude.edtSearching.text = null
                viewModel.search(null)
            }
            setRootView(binding.searchingInclude)
            setTextWatcher(createTextWatcher())
            endSetup()
            isShowIconVisible(true)
        }
    }

    private fun startSearch() {
        val word = getTextFromEdtSearching()
        if (word.isEmpty()) return
        viewModel.search(word)
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            startSearch()
        }
    }

    private fun getTextFromEdtSearching() =
        binding.searchingInclude.edtSearching.text.toString().trim()

    private fun createMap() = SparseArray<DocumentType>().apply {
        this[binding.typeTxt.id] = DocumentType.TXT
        this[binding.typePdf.id] = DocumentType.PDF
        this[binding.typeEpub.id] = DocumentType.EPUB
        this[binding.typeB2.id] = DocumentType.B2
        this[binding.typeDoc.id] = DocumentType.DOC
        this[binding.typeDocx.id] = DocumentType.DOCX
        this[binding.typePpt.id] = DocumentType.PPT
        this[binding.typeOther.id] = DocumentType.OTHER
    }

    override fun onStop() {
        super.onStop()
        DocumentsFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}