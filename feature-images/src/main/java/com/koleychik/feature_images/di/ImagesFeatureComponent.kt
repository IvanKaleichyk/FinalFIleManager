package com.koleychik.feature_images.di

import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.di.modules.ViewModelsModule
import com.koleychik.feature_images.ui.ImagesFragment
import dagger.Component

@Component(
    modules = [ViewModelsModule::class],
    dependencies = [ImagesFeatureDependencies::class]
)
interface ImagesFeatureComponent : ImagesFeatureApi {

    fun inject(fragment: ImagesFragment)

    companion object {
        fun initAndGet(dependencies: ImagesFeatureDependencies): ImagesFeatureComponent =
            DaggerImagesFeatureComponent.builder().imagesFeatureDependencies(dependencies)
                .build()
    }

}