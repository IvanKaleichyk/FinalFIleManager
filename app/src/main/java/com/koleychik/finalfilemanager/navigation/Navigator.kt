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
                R.id.selectCategoryFragment -> selectCategoryApi.get()
                R.id.imagesFragment -> imagesFeatureApi.get()
                R.id.musicFragment -> musicFeatureApi.get()
                R.id.documentsFragment -> documentsFeatureApi.get()
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