package com.koleychik.feature_music.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_music.databinding.FragmentMusicBinding
import com.koleychik.feature_music.di.MusicFeatureComponentHolder
import com.koleychik.feature_music.ui.adapters.MusicAdapter
import com.koleychik.feature_music.ui.viewModel.MusicViewModel
import com.koleychik.feature_music.ui.viewModel.ViewModelFactory
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
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

    private fun createRv() {
        binding.carcass.rv.adapter = adapter
//        adapter.onClick = onClick
    }

    private fun createSwipeToRefresh() {
        with(binding.carcass.swipeToRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.getMusic(getTextFromEdtSearching())
            }
        }
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

    private fun showList(list: List<MusicModel>) {
        adapter.submitList(list)
        binding.carcass.rv.visibility = View.VISIBLE
    }

    private fun emptyList() {
        binding.carcass.infoText.visibility = View.VISIBLE
    }

    private fun loading() {
        loadingApi.apply {
            setVisible(true)
            startAnimation()
        }
        viewModel.getMusic(getTextFromEdtSearching())
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
        loadingApi.apply {
            setVisible(false)
            endAnimation()
        }
        with(binding.carcass) {
            infoText.visibility = View.GONE
            rv.visibility = View.INVISIBLE
            swipeToRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}