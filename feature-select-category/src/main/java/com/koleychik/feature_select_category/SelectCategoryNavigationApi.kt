package com.koleychik.feature_select_category

import android.os.Bundle
import com.koleychik.module_injector.navigation.NavigatorApi

interface SelectCategoryNavigationApi : NavigatorApi {

    fun selectCategoryFeatureGoToMusicFragment(bundle: Bundle? = null)
    fun selectCategoryFeatureGoToImagesFragment(bundle: Bundle? = null)
    fun selectCategoryFeatureGoToDocumentsFragment(bundle: Bundle? = null)
    fun selectCategoryFeatureGoToVideoFragment(bundle: Bundle? = null)
}