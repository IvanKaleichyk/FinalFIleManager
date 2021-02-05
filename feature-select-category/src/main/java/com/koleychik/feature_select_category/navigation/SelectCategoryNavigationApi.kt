package com.koleychik.feature_select_category.navigation

import com.koleychik.module_injector.navigation.NavigatorApi

interface SelectCategoryNavigationApi: NavigatorApi {

    fun goToMusicFragment()
    fun goToImagesFragment()
    fun goToDocumentsFragment()
    fun goToVideoFragment()
}