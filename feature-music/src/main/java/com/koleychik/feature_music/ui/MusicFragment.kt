package com.koleychik.feature_music.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_music.databinding.FragmentMusicBinding
import com.koleychik.feature_music.di.MusicFeatureComponentHolder
import com.koleychik.feature_music.ui.adapters.MusicAdapter
import com.koleychik.feature_music.ui.viewModel.MusicViewModel
import com.koleychik.feature_music.ui.viewModel.ViewModelFactory
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.fileCarcass.MusicModel
import javax.inject.Inject

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null

    private val binding get() = _binding!!

    @Inject
    internal lateinit var adapter: MusicAdapter

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MusicViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MusicViewModel::class.java]
    }

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var searchingUIApi: SearchingUIApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentMusicBinding.inflate(layoutInflater, container, false)
        MusicFeatureComponentHolder.getComponent().inject(this)
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
                it == null -> viewModel.getFirstTimeMusic(getTextFromEdtSearching())
                it.isEmpty() -> emptyList()
                else -> showList(it)
            }
        })
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


    private fun startSearch() {
        val word = getTextFromEdtSearching()
        if (word.isEmpty()) return
        viewModel.search(word)
    }

    private fun createRv() {
        binding.carcass.rv.run {
            itemAnimator = DefaultItemAnimator()
            adapter = this@MusicFragment.adapter
        }
        adapter.onClick = { viewModel.openFile(it) }

    }

    private fun createSwipeToRefresh() {
        with(binding.carcass.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getMusic(getTextFromEdtSearching())
            }
        }
    }

    private fun showList(list: List<MusicModel>) {
        adapter.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
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

    private fun resetViews() {
        with(binding.carcass) {
            infoText.visibility = View.GONE
            rv.visibility = View.INVISIBLE
            swipeToRefresh.isRefreshing = false
        }
    }

    override fun onStop() {
        super.onStop()
        MusicFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}