package com.koleychik.feature_video.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_video.databinding.FragmentVideoBinding
import com.koleychik.feature_video.di.VideoFeatureComponentHolder
import com.koleychik.feature_video.ui.viewModel.VideoViewModel
import com.koleychik.feature_video.ui.viewModel.ViewModelFactory
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
        _binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStub()
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

    private fun loading() {
        loadingApi.run {
            setVisible(true)
            startAnimation()
        }
        viewModel.getVideo()
    }

    private fun emptyList() {
        binding.infoText.visibility = View.VISIBLE
    }

    private fun showList(list: List<VideoModel>) {
        adapterApi.submitList(list)
        binding.rv.visibility = View.VISIBLE
    }

    private fun resetUI() {
        with(binding) {
            rv.visibility = View.INVISIBLE
            infoText.visibility = View.GONE
            swipeToRefresh.isRefreshing = false
        }
        loadingApi.run {
            endAnimation()
            setVisible(false)
        }
    }

    private fun createRv() {
        with(binding) {
            rv.layoutManager = GridLayoutManager(context, 2)
            rv.adapter = adapterApi
            rv.setHasFixedSize(true)
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
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = true
            viewModel.getVideo()
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
        VideoFeatureComponentHolder.reset()
    }
}