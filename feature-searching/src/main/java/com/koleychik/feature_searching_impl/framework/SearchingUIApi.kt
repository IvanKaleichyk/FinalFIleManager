package com.koleychik.feature_searching_impl.framework

import android.text.TextWatcher
import com.koleychik.feature_searching_impl.databinding.LayoutSearchingBinding

interface SearchingUIApi {

    fun endSetup()
    fun getSearchLayoutId(): Int
    fun setOnCloseSearching(onClose: () -> Unit)
    fun setTextWatcher(textWatcher: TextWatcher)
    fun setRootView(binding: LayoutSearchingBinding)
    fun isShowIconVisible(boolean: Boolean)
}
