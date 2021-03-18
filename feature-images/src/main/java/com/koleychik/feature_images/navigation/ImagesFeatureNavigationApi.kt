package com.koleychik.feature_images.navigation

import android.os.Bundle
import androidx.navigation.NavController

interface ImagesFeatureNavigationApi {

    fun imagesFeatureGoToImageInfo(navController: NavController?, bundle: Bundle? = null)

}