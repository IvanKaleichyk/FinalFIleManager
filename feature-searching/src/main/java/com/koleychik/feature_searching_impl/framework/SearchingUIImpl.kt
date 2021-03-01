package com.koleychik.feature_searching_impl.framework

import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.koleychik.feature_searching_impl.R
import com.koleychik.feature_searching_impl.databinding.LayoutSearchingBinding
import javax.inject.Inject

internal class SearchingUIImpl @Inject constructor() : SearchingUIApi {

    private var onClose: (() -> Unit)? = null

    private var textWatcher: TextWatcher? = null

    private var isSearchingOpen = false

    private lateinit var binding: LayoutSearchingBinding

    override fun endSetup() {
        with(binding) {
            edtSearching.addTextChangedListener(textWatcher)
            fabSearch.setOnClickListener(createOnClickToFabIcon())
            motionLayoutSearching.visibility = View.VISIBLE
        }
    }

    private fun createOnClickToFabIcon() = View.OnClickListener {
        isSearchingOpen = !isSearchingOpen
        if (isSearchingOpen) openEdt()
        else closeEdt()
    }

    private fun openEdt() {
        with(binding) {
            motionLayoutSearching.run {
                setTransitionDuration(500)
                transitionToState(R.id.open)
            }
            fabSearch.setImageResource(R.drawable.close_icon_32_black)
        }
    }

    private fun closeEdt() {
        onClose?.let { close -> close() }
        with(binding) {
            motionLayoutSearching.run {
                setTransitionDuration(500)
                transitionToState(R.id.close)
            }
            fabSearch.setImageResource(R.drawable.search_icon_32_black)
        }
    }

    override fun getSearchLayoutId(): Int = R.layout.layout_searching

    override fun setOnCloseSearching(onClose: () -> Unit) {
        this.onClose = onClose
    }

    override fun setTextWatcher(textWatcher: TextWatcher) {
        this.textWatcher = textWatcher
    }

    override fun setRootView(binding: LayoutSearchingBinding) {
        this.binding = binding
    }

    override fun isShowIconVisible(boolean: Boolean) {
        binding.fabSearch.isVisible = boolean
    }
}