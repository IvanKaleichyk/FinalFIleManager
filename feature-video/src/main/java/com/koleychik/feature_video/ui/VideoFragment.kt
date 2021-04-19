package com.koleychik.feature_video.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.feature_video.databinding.FragmentVideoBinding
import com.koleychik.feature_video.di.VideoFeatureComponentHolder
import com.koleychik.feature_video.ui.viewModel.VideoViewModel
import com.koleychik.feature_video.ui.viewModel.ViewModelFactory
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.fileCarcass.media.MediaCarcass
import com.koleychik.models.fileCarcass.media.VideoModel
import javax.inject.Inject

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var adapterApi: RvMediaAdapterApi

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var searchingUIApi: SearchingUIApi

    private val viewModel: VideoViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[VideoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        VideoFeatureComponentHolder.getComponent().inject(this)
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
        viewModel.currentList.observe(viewLifecycleOwner, {
            resetUI()
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
        viewModel.getVideo(getTextFromEdtSearching())
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun showList(list: List<VideoModel>) {
        adapterApi.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun resetUI() {
        with(binding.carcass) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
        loadingApi.run {
            endAnimation()
            setVisible(false)
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

    private fun createRv() {
        with(binding.carcass.rv) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterApi
            setHasFixedSize(true)
        }
        adapterApi.setOnCLick { model, _ ->
            openFile(model)
        }
    }

    private fun openFile(model: MediaCarcass) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = model.uri
        val intentOpen = Intent.createChooser(intent, "Choose an application to open with:")
        ContextCompat.startActivity(binding.root.context, intentOpen, Bundle())
    }

    private fun createSwipeToRefresh() {
        binding.carcass.swipeToRefresh.setOnRefreshListener {
            binding.carcass.swipeToRefresh.isRefreshing = true
            viewModel.getVideo(getTextFromEdtSearching())
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

    private fun getTextFromEdtSearching() =
        binding.searchingInclude.edtSearching.text.toString().trim()

    override fun onStop() {
        super.onStop()
        VideoFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}