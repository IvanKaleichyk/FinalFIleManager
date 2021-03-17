package com.koleychik.feature_nav_bar.framework

interface NavHosts {

    fun onDestinationChange(id: Int)
    fun fileNavHost(): Int
    fun folderNavHost(): Int

}