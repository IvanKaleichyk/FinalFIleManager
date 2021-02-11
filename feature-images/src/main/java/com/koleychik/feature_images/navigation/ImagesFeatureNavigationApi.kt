package com.koleychik.feature_images.navigation

import android.os.Bundle
import com.koleychik.module_injector.navigation.NavigatorApi

interface ImagesFeatureNavigationApi : NavigatorApi {

    fun imagesFeatureGoToImageInfo(bundle: Bundle? = null)

}