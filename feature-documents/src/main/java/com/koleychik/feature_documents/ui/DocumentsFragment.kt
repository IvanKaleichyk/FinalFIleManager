package com.koleychik.feature_documents.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.koleychik.basic_resources.Constants.TAG
import com.koleychik.feature_documents.R
import com.koleychik.feature_documents.databinding.FragmentDocumentsBinding
import com.koleychik.feature_documents.di.DocumentsFeatureComponentHolder
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModel
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentType
import javax.inject.Inject

class DocumentsFragment : Fragment() {

    private var _binding: FragmentDocumentsBinding? = null

    private val binding get() = _binding!!

    private val mapTypes: Map<Int, DocumentType> by lazy { createMap() }

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
        _binding = FragmentDocumentsBinding.inflate(layoutInflater, container, false)
        DocumentsFeatureComponentHolder.getComponent().inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mapTypes = createMap()

        setupViewStub()
        setupSearching()
        createOnClickListener()
        createRv()
        createSwipeToRefresh()
        subscribe()
    }

    private fun subscribe() {
        viewModel.currentList.observe(viewLifecycleOwner, Observer {
            resetUI()
            when {
                it == null -> loading()
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
        viewModel.listSelectFormats.observe(viewLifecycleOwner, Observer {
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


    private fun loading() {
        Log.d(TAG, "start loading")
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.getDocuments(getTextFromEdtSearching())
    }


    private fun showList(list: List<FileCarcass>) {
        Log.d(TAG, "show list")
        adapterApi.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        Log.d(TAG, "show list")
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun resetUI() {
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
        loadingApi.run {
            setVisible(false)
            endAnimation()
        }
    }

    private fun delete() {

    }

    private fun createSwipeToRefresh() {
        binding.carcass.swipeToRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
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
            addItemDecoration(itemDecoration)
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
                viewModel.currentList.value = viewModel.fullList.value
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
        resetUI()
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
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

    private fun createMap() = mapOf(
        binding.typeTxt.id to DocumentType.TXT,
        binding.typePdf.id to DocumentType.PDF,
        binding.typeEpub.id to DocumentType.EPUB,
        binding.typeB2.id to DocumentType.B2,
        binding.typeDoc.id to DocumentType.DOC,
        binding.typeDocx.id to DocumentType.DOCX,
        binding.typePpt.id to DocumentType.PPT,
        binding.typeOther.id to DocumentType.OTHER
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}