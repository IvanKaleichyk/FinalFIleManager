package com.koleychik.feature_searching_impl.framework

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
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
            edtSearching.run {
                addTextChangedListener(textWatcher)
                onFocusChangeListener = createOnFocusChangeListener(context)
            }
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
            setEditTextFocus(true)
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
            setEditTextFocus(false)
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

    fun setEditTextFocus(isFocused: Boolean) {
        with(binding.edtSearching) {
            isCursorVisible = isFocused
            isFocusable = isFocused
            isFocusableInTouchMode = isFocused
            if (isFocused) {
                requestFocus()
            }
        }
    }

    private fun createOnFocusChangeListener(context: Context) =
        View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // Open keyboard
                (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                    binding.edtSearching,
                    InputMethodManager.SHOW_FORCED
                )
            } else {
                // Close keyboard
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.edtSearching.windowToken,
                    0
                )
            }
        }

}