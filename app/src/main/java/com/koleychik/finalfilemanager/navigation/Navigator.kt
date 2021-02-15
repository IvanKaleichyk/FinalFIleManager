package com.koleychik.finalfilemanager.navigation

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import com.koleychik.feature_documents.di.DocumentsFeatureApi
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_music.di.MusicFeatureApi
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationApi
import com.koleychik.finalfilemanager.R
import javax.inject.Provider

class Navigator(
    private val selectCategoryApi: Provider<SelectCategoryApi>,
    private val imagesFeatureApi: Provider<ImagesFeatureApi>,
    private val musicFeatureApi: Provider<MusicFeatureApi>,
    private val documentsFeatureApi: Provider<DocumentsFeatureApi>
) : ImagesFeatureNavigationApi, SelectCategoryNavigationApi {

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    fun bind(newNavController: NavController) {
        _navController = newNavController.apply {
            addOnDestinationChangedListener(createSetOnDestinationListener())
        }
    }

    fun unbind() {
        _navController = null
    }

    private fun createSetOnDestinationListener() =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.selectCategoryFragment -> selectCategoryApi.get()
                R.id.imagesFragment -> imagesFeatureApi.get()
                R.id.musicFragment -> musicFeatureApi.get()
                R.id.documentsFragment -> documentsFeatureApi.get()
            }
            Log.d("MAIN_APP_TAG", "destination changed id is " + destination.id)
        }

    override fun imagesFeatureGoToImageInfo(bundle: Bundle?) {
        if (navController.currentDestination?.id == R.id.imagesFragment) {
            navController.navigate(R.id.action_imagesFragment_to_imageInfoFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToMusicFragment(bundle: Bundle?) {
        if (navController.currentDestination?.id == R.id.selectCategoryFragment) {
            navController.navigate(R.id.action_selectCategoryFragment_to_musicFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToImagesFragment(bundle: Bundle?) {
        if (navController.currentDestination?.id == R.id.selectCategoryFragment) {
            navController.navigate(R.id.action_selectCategoryFragment_to_imagesFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToDocumentsFragment(bundle: Bundle?) {
        if (navController.currentDestination?.id == R.id.selectCategoryFragment) {
            navController.navigate(R.id.action_selectCategoryFragment_to_documentsFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToVideoFragment(bundle: Bundle?) {
        if (navController.currentDestination?.id == R.id.selectCategoryFragment) {
//            navController.navigate(R.id., bundle)
        }
    }

}