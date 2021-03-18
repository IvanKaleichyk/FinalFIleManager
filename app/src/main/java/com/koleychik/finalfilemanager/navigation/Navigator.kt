package com.koleychik.finalfilemanager.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.koleychik.feature_documents.di.DocumentsFeatureApi
import com.koleychik.feature_documents.ui.DocumentsFragment
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureApi
import com.koleychik.feature_folders_and_files.navigation.FoldersAndFilesNavigationApi
import com.koleychik.feature_folders_and_files.ui.FoldersAndFilesFragment
import com.koleychik.feature_image_info.di.ImageInfoFeatureApi
import com.koleychik.feature_image_info.ui.ImageInfoFragment
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_images.ui.ImagesFragment
import com.koleychik.feature_music.di.MusicFeatureApi
import com.koleychik.feature_music.ui.MusicFragment
import com.koleychik.feature_nav_bar.di.NavBarFeatureApi
import com.koleychik.feature_nav_bar.framework.NavBarFragment
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_select_category.SelectCategoryNavigationApi
import com.koleychik.feature_select_category.ui.SelectCategoryFragment
import com.koleychik.feature_video.di.VideoFeatureApi
import com.koleychik.feature_video.ui.VideoFragment
import com.koleychik.finalfilemanager.R
import com.koleychik.injector.AppConstants.TAG
import com.koleychik.injector.NavigationSystem
import javax.inject.Provider

class Navigator(
    private val selectCategoryApi: Provider<SelectCategoryApi>,
    private val imagesFeatureApi: Provider<ImagesFeatureApi>,
    private val musicFeatureApi: Provider<MusicFeatureApi>,
    private val documentsFeatureApi: Provider<DocumentsFeatureApi>,
    private val videoFeatureApi: Provider<VideoFeatureApi>,
    private val imageInfoFeatureApi: Provider<ImageInfoFeatureApi>,
    private val foldersAndFilesFeatureApi: Provider<FoldersAndFilesFeatureApi>,
    private val navBarFeatureApi: Provider<NavBarFeatureApi>
) : ImagesFeatureNavigationApi,
    SelectCategoryNavigationApi, FoldersAndFilesNavigationApi {

    init {
        NavigationSystem.onStartFeature = { startFragment(it) }
    }

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun startFragment(fragment: Fragment) {
        when (fragment) {
            is SelectCategoryFragment -> selectCategoryApi.get()
            is ImagesFragment -> imagesFeatureApi.get()
            is MusicFragment -> musicFeatureApi.get()
            is DocumentsFragment -> documentsFeatureApi.get()
            is VideoFragment -> videoFeatureApi.get()
            is ImageInfoFragment -> imageInfoFeatureApi.get()
            is FoldersAndFilesFragment -> foldersAndFilesFeatureApi.get()
            is NavBarFragment -> navBarFeatureApi.get()
        }
    }

    override fun imagesFeatureGoToImageInfo(navController: NavController?, bundle: Bundle?) {
        Log.d(TAG, "imagesFeatureGoToImageInfo")
        val controller = navController ?: this.navController
        if (controller == null) Log.d(TAG, "controller == null")
        if (controller?.currentDestination?.id == R.id.navBarFragment) {
            controller.navigate(R.id.action_navBarFragment_to_imageInfoFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToMusicFragment(
        controller: NavController,
        bundle: Bundle?
    ) {
        if (controller.currentDestination?.id == R.id.selectCategoryFragment) {
            controller.navigate(R.id.action_selectCategoryFragment_to_musicFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToImagesFragment(
        controller: NavController,
        bundle: Bundle?
    ) {
        if (controller.currentDestination?.id == R.id.selectCategoryFragment) {
            controller.navigate(R.id.action_selectCategoryFragment_to_imagesFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToDocumentsFragment(
        controller: NavController,
        bundle: Bundle?
    ) {
        if (controller.currentDestination?.id == R.id.selectCategoryFragment) {
            controller.navigate(R.id.action_selectCategoryFragment_to_documentsFragment, bundle)
        }
    }

    override fun selectCategoryFeatureGoToVideoFragment(
        controller: NavController,
        bundle: Bundle?
    ) {
        if (controller.currentDestination?.id == R.id.selectCategoryFragment) {
            controller.navigate(R.id.action_selectCategoryFragment_to_videoFragment, bundle)
        }
    }

    override fun openFileInNewFragment(controller: NavController, bundle: Bundle) {
        if (controller.currentDestination?.id == R.id.foldersAndFilesFragment) {
            controller.navigate(R.id.action_foldersAndFilesFragment_self, bundle)
        }
    }
}