package com.koleychik.feature_images.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.basic_resources.Constants.PARCELABLE_LIST
import com.koleychik.basic_resources.Constants.PARCELABLE_POSITION
import com.koleychik.feature_images.databinding.FragmentImagesBinding
import com.koleychik.feature_images.di.ImagesFeatureComponentHolder
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_images.ui.viewModels.ImagesViewModel
import com.koleychik.feature_images.ui.viewModels.ImagesViewModelFactory
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.fileCarcass.media.ImageModel
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

    @Inject
    internal lateinit var searchingUIApi: SearchingUIApi

    @Inject
    internal lateinit var navigationApi: ImagesFeatureNavigationApi

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ImagesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
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
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (binding.carcass.swipeToRefresh.isRefreshing) return@observe

            if (isLoading) startLoading()
            else stopLoading()
        }
        viewModel.currentList.observe(viewLifecycleOwner, {
            resetViews()
            when {
                it == null -> viewModel.getFirstTimeImagesData(getTextFromEdtSearching())
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
    }

    private fun startSearch() {
        val word = getTextFromEdtSearching()
        if (word.isEmpty()) return
        viewModel.search(word)
    }

    private fun startLoading(){
        resetViews()
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
    }

    private fun stopLoading(){
        loadingApi.run {
            setVisible(false)
            endAnimation()
        }
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun showList(list: List<ImageModel>) {
        adapter.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun createSwipeToRefresh() {
        with(binding.carcass.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getImages(getTextFromEdtSearching())
            }
        }
    }

    private fun createRv() {
        with(binding.carcass.rv) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@ImagesFragment.adapter
            setHasFixedSize(true)
        }
        adapter.setOnCLick { _, position -> createOnCLick(position) }
    }

    private fun createOnCLick(position: Int) {
        val bundle = Bundle().apply {
            putInt(PARCELABLE_POSITION, position)
            putParcelableArrayList(PARCELABLE_LIST, viewModel.currentList.value!! as ArrayList)
        }
        navigationApi.imagesFeatureGoToImageInfo(bundle)
    }

    private fun resetViews() {
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
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

    private fun getTextFromEdtSearching() =
        binding.searchingInclude.edtSearching.text.toString().trim()

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            startSearch()
        }
    }

    private fun setupViewStub() {
        loadingApi.setRootView(requireView())
        binding.carcass.viewStub.apply {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
    }

    override fun onStop() {
        super.onStop()
        ImagesFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}