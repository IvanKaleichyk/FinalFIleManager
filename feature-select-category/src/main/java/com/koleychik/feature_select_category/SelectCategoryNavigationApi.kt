package com.koleychik.feature_select_category

import android.os.Bundle
import androidx.navigation.NavController

interface SelectCategoryNavigationApi {
    fun selectCategoryFeatureGoToMusicFragment(controller: NavController, bundle: Bundle? = null)
    fun selectCategoryFeatureGoToImagesFragment(controller: NavController, bundle: Bundle? = null)
    fun selectCategoryFeatureGoToDocumentsFragment(
        controller: NavController,
        bundle: Bundle? = null
    )

    fun selectCategoryFeatureGoToVideoFragment(controller: NavController, bundle: Bundle? = null)
}