package com.koleychik.feature_searching_api

import android.text.TextWatcher
import android.view.View

interface SearchingUIApi {

    fun endSetup()
    fun getSearchLayoutId(): Int
    fun setOnCloseSearching(onClose: () -> Unit)
    fun setTextWatcher(textWatcher: TextWatcher)
    fun setRootView(rootView: View)
    fun isShowIconVisible(boolean: Boolean)

}