package com.koleychik.feature_images.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_images.databinding.FragmentImagesBinding
import com.koleychik.feature_images.di.ImagesFeatureComponentHolder
import com.koleychik.feature_images.ui.viewModels.ImagesViewModel
import com.koleychik.feature_images.ui.viewModels.ImagesViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.*
import javax.inject.Inject

class ImagesFragment : Fragment() {

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var loadingApi: LoadingApi

    @Inject
    lateinit var adapter: RvMediaAdapterApi

    @Inject
    lateinit var viewModelFactory: ImagesViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ImagesViewModel::class.java]
    }

    private val coroutineScore = CoroutineScope(Job() + Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(layoutInflater, container, false)
        ImagesFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStub()
        setupSearching()
        createRv()
        createSwipeToRefresh()
        subscribe()
    }

    private fun subscribe() {
        viewModel.list.observe(viewLifecycleOwner, {
            resetViews()
            when {
                it == null -> loading()
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
        viewModel.searchingWord.observe(viewLifecycleOwner, {
            if (it == null || it == "") {
                if (searchingApi.getCurrentList().isNotEmpty()) viewModel.list.value =
                    searchingApi.getCurrentList()
            }
            else startSearch(it)
        })
    }

    private fun startSearch(searchWord: String) {
        resetViews()
        loadingApi.startAnimation()
        coroutineScore.cancel(searchWord)
        searchByName(searchWord)
    }

    private fun searchByName(searchWord: String) {
        coroutineScore.launch {
            val newList = searchingApi.searchByName(searchWord)
            withContext(Dispatchers.Main) {
                viewModel.list.value = newList
            }
        }
    }

    private fun loading() {
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.getImages()
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun showList(list: List<ImageModel>) {
        searchingApi.setFullList(list)
        adapter.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun createSwipeToRefresh() {
        with(binding.carcass.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getImages()
            }
        }
    }

    private fun createRv() {
        with(binding.carcass) {
            rv.layoutManager = GridLayoutManager(context, 2)
            rv.adapter = adapter
            rv.setHasFixedSize(true)
        }
    }

    private fun resetViews() {
        loadingApi.apply {
            setVisible(false)
            endAnimation()
        }
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun setupSearching() {
        binding.searchingViewStub.run {
            layoutResource = searchingApi.getSearchLayoutId()
            inflate()
        }
        searchingApi.run {
            setRootView(binding.searchingViewStub)
            setTextWatcher(createTextWatcher())
            endSetup()
        }
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            viewModel.searchingWord.value = (viewModel.searchingWord.value.toString() + s).trim()
        }

    }

    private fun setupViewStub() {
        loadingApi.setRootView(requireView())
        binding.carcass.viewStub.apply {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        coroutineScore.cancel()
        ImagesFeatureComponentHolder.reset()
    }
}