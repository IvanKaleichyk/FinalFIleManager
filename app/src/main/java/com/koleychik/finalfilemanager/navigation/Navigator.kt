package com.koleychik.finalfilemanager.navigation

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationApi
import javax.inject.Provider

class Navigator(
    imagesFeatureApi: Provider<ImagesFeatureApi>,
    selectCategoryApi: Provider<SelectCategoryApi>
) : ImagesFeatureNavigationApi, SelectCategoryNavigationApi {

    private var navController: NavController? = null

    fun bind(newNavController: NavController) {
        navController = newNavController.apply {
            createSetOnDestinationListener()
        }
    }

    fun unbind() {
        navController = null
    }

    private fun createSetOnDestinationListener() =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
            }
            Log.d("MAIN_APP", "destination changed id is " + destination.id)
        }

    override fun goToImageInfo(bundle: Bundle?) {

    }

    override fun goToMusicFragment() {

    }

    override fun goToImagesFragment() {

    }

    override fun goToDocumentsFragment() {

    }

    override fun goToVideoFragment() {

    }
}