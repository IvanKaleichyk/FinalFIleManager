package com.koleychik.feature_loading_api

import android.view.View

interface LoadingApi {
    fun toStart()
    fun toEnd()
    fun setRootView(view: View)
    fun startAnimation()
    fun endAnimation()
    fun setVisible(isVisible: Boolean)
    fun getLayoutRes(): Int
}