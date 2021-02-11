package com.koleychik.feature_images.navigation

import com.koleychik.feature_images.ImagesFeatureDestroyer
import com.koleychik.module_injector.navigation.NavigatorComponentHolder

object ImagesFeatureNavigationComponentHolder :
    NavigatorComponentHolder<ImagesFeatureNavigationApi> {

    private var navigationApi: ImagesFeatureNavigationApi? = null

    override fun init(api: ImagesFeatureNavigationApi) {
        navigationApi = api
    }

    override fun get(): ImagesFeatureNavigationApi = navigationApi!!

    override fun reset() {
        navigationApi = null
    }
}