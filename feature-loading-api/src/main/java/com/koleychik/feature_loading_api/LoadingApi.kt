package com.koleychik.feature_loading_api

interface LoadingApi {
    fun toStart()
    fun toEnd()
    fun startAnimation()
    fun endAnimation()
    fun setVisible(isVisible: Boolean)
    fun getLayoutRes(): Int
}