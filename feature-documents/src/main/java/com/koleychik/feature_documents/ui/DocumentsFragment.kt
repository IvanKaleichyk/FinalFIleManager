package com.koleychik.feature_documents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.koleychik.feature_documents.R
import com.koleychik.feature_documents.databinding.FragmentDocumentsBinding
import com.koleychik.feature_documents.di.DocumentsFeatureComponentHolder
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModel
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentType
import javax.inject.Inject

class DocumentsFragment : Fragment() {

    private var _binding: FragmentDocumentsBinding? = null

    private val binding get() = _binding!!

    private lateinit var mapTypes: Map<Int, MutableLiveData<DocumentType>>

    @Inject
    internal lateinit var loadingApi: LoadingApi

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
        createOnCLickListener()
        createRv()
        createSwipeToRefresh()
        subscribe()
    }

    private fun subscribe() {
        viewModel.list.observe(viewLifecycleOwner, {
            resetUI()
            when {
                it == null -> loading()
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
    }

    private fun createOnCLickListener() {
        val onClickListener = View.OnClickListener {
            val documentType = mapTypes[it.id]?.value ?: return@OnClickListener
            var isSelected = documentType.isSelected
            mapTypes[it.id]?.value?.isSelected = !isSelected
            isSelected = !isSelected
            if (isSelected) {
                (it as TextView).setBackgroundResource(R.drawable.bg_type_document)
                viewModel.addToListDocumentsWithType(documentType)
            } else {
                (it as TextView).setBackgroundResource(R.drawable.bg_type_document_dont_selected)
                viewModel.deleteDocumentsWithType(documentType)
            }
        }

        with(binding) {
            typeTxt.setOnClickListener(onClickListener)
            typePdf.setOnClickListener(onClickListener)
            typeEpub.setOnClickListener(onClickListener)
            typeB2.setOnClickListener(onClickListener)
            typeDocx.setOnClickListener(onClickListener)
            typeDoc.setOnClickListener(onClickListener)
            typePpt.setOnClickListener(onClickListener)
            typeOther.setOnClickListener(onClickListener)
        }
    }

    private fun loading() {
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.getDocuments()
    }

//    private fun createMap() = mapOf(
//        binding.typeTxt.id to viewModel.listTypes[0],
//        binding.typePdf.id to viewModel.listTypes[1],
//        binding.typeEpub.id to viewModel.listTypes[2],
//        binding.typeB2.id to viewModel.listTypes[3],
//        binding.typeDoc.id to viewModel.listTypes[4],
//        binding.typeDocx.id to viewModel.listTypes[5],
//        binding.typePpt.id to viewModel.listTypes[6],
//        binding.typeOther.id to viewModel.listTypes[7],
//    )


    private fun showList(list: List<FileCarcass>) {
        adapterApi.submitList(list)
        binding.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        binding.infoText.visibility = View.VISIBLE
    }

    private fun resetUI() {
        with(binding) {
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
        binding.swipeToRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.getDocuments()
            }
        }
    }

    private fun createRv() {
        val itemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayout.VERTICAL
        )
        binding.rv.apply {
            adapter = adapterApi
            addItemDecoration(itemDecoration)
        }
    }

    private fun setupViewStub() {
        loadingApi.setRootView(requireView())
        binding.viewStub.apply {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}