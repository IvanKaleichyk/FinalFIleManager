package com.koleychik.feature_folders_and_files.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.koleychik.basic_resources.Constants.PATH
import com.koleychik.core_files.FilesCoreConstants.ROOT_PATH
import com.koleychik.feature_folders_and_files.databinding.FragmentFoldersAndFilesBinding
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureComponentHolder
import com.koleychik.feature_folders_and_files.navigation.FoldersAndFilesNavigationApi
import com.koleychik.feature_folders_and_files.ui.viewModel.FoldersAndFilesViewModel
import com.koleychik.feature_folders_and_files.ui.viewModel.ViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.FolderModel
import javax.inject.Inject

class FoldersAndFilesFragment : Fragment() {

    private var _binding: FragmentFoldersAndFilesBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var adapterApi: RvFilesAdapterApi

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var searchingUIApi: SearchingUIApi

    @Inject
    internal lateinit var navigatorApi: FoldersAndFilesNavigationApi

    private val path by lazy {
        try {
            requireArguments().getString(PATH, ROOT_PATH) ?: ROOT_PATH
        } catch (E: IllegalStateException) {
            ROOT_PATH
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FoldersAndFilesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentFoldersAndFilesBinding.inflate(inflater, container, false)
        FoldersAndFilesFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStub()
        setupSearching()
        createRv()
        createOnClick()
        createOnSwipeToRefresh()
        updateUI()
        subscribe()
    }

    private fun subscribe() {
        viewModel.currentList.observe(viewLifecycleOwner, {
            resetViews()
            when {
                it == null -> loading()
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
    }

    private fun loading() {
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.getFoldersAndFiles(path, getTextFromEdtSearching())
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun showList(list: List<FileCarcass>) {
        binding.carcass.rv.visibility = View.VISIBLE
        adapterApi.submitList(list)
    }

    private fun createOnClick() {
        adapterApi.onClick = { model, _ ->
            if (model is FolderModel) openFolder(model)
            else viewModel.openFile(model)
        }
    }

    private fun openFolder(model: FolderModel) {
        navigatorApi.openFileInNewFragment(
            findNavController(),
            createBundleForNavigation(model)
        )
    }

    private fun createBundleForNavigation(model: FileCarcass) = Bundle().apply {
        putString(PATH, model.uri.path)
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
        resetViews()
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.search(word)
    }

    private fun getTextFromEdtSearching() =
        binding.searchingInclude.edtSearching.text.toString().trim()

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            startSearch()
        }
    }

    private fun createOnSwipeToRefresh() {
        binding.carcass.swipeToRefresh.run {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getFoldersAndFiles(path, getTextFromEdtSearching())
            }
        }
    }

    private fun updateUI() {
        binding.path.text = this@FoldersAndFilesFragment.path
    }

    private fun createRv() {
        binding.carcass.rv.adapter = adapterApi
    }

    private fun setupViewStub() {
        loadingApi.setRootView(requireView())
        binding.carcass.viewStub.run {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
    }

    private fun resetViews() {
        loadingApi.run {
            setVisible(false)
            endAnimation()
        }
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        FoldersAndFilesFeatureComponentHolder.reset()
    }
}