package com.koleychik.feature_music

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_music.adapters.MusicAdapter
import com.koleychik.feature_music.databinding.FragmentMusicBinding
import com.koleychik.feature_music.di.MusicFeatureComponentHolder
import com.koleychik.models.fileCarcass.MusicModel
import javax.inject.Inject

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: MusicAdapter

    @Inject
    lateinit var viewModel: MusicViewModel

    @Inject
    lateinit var loadingApi: LoadingApi

//    @Inject
//    lateinit var mediaController: MediaControllerCompat

    private val onClick: ((model: MusicModel) -> Unit) by lazy {
        {
            viewModel.setData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicBinding.inflate(layoutInflater, container, false)
        MusicFeatureComponentHolder.getComponent().inject(this)
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewStub()
        createRv()
        createSwipeToRefresh()
        createOnCLickListener()
        subscribe()
    }

    private fun createOnCLickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
//                R.id.previous -> mediaController.transportControls.skipToPrevious()
//                R.id.pauseOrPlay -> playOrPause()
//                R.id.next -> mediaController.transportControls.skipToNext()
            }
        }
        with(binding) {
            previous.setOnClickListener(onClickListener)
            pauseOrPlay.setOnClickListener(onClickListener)
            next.setOnClickListener(onClickListener)
        }
    }

//    private fun playOrPause() {
//        if (viewModel.isPlaying.value == true) mediaController.transportControls.pause()
//        else mediaController.transportControls.play()
//
//    }

    private fun createRv() {
        binding.rv.adapter = adapter
        adapter.onClick = onClick
    }

    private fun createSwipeToRefresh() {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                loading()
            }
        }
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
        viewModel.currentAudio.observe(viewLifecycleOwner, {
            if (it == null) binding.motionLayout.transitionToState(R.id.footerClosed)
            else {
                updateData(it)
                binding.motionLayout.transitionToState(R.id.footerOpen)
            }
        })
        viewModel.isPlaying.observe(viewLifecycleOwner, {
            if (it) binding.pauseOrPlay.setImageResource(R.drawable.pause_icon_48_black)
            else binding.pauseOrPlay.setImageResource(R.drawable.play_icon_48_black)
        })
    }

    private fun showList(list: List<MusicModel>) {
        adapter.submitList(list)
        binding.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        binding.infoText.visibility = View.VISIBLE
    }

    private fun loading() {
        loadingApi.apply {
            setVisible(true)
            startAnimation()
        }
        viewModel.load()
    }

    private fun setupViewStub() {
        binding.viewStub.apply {
            layoutResource = loadingApi.getLayoutRes()
            inflate()
        }
        loadingApi.setRootView(requireView())
    }

    private fun updateData(model: MediaMetadataCompat) {
        with(binding) {
            textTitle.text = model.description.title
            textAuthor.text = model.description.subtitle
        }
    }

    private fun resetViews() {
        with(binding) {
            infoText.visibility = View.GONE
            rv.visibility = View.INVISIBLE
            swipeToRefresh.isRefreshing = false
        }
        loadingApi.apply {
            setVisible(false)
            endAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}