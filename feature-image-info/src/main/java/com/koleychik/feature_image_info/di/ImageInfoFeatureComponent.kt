package com.koleychik.feature_image_info.di

import com.koleychik.feature_image_info.di.modules.ImageInfoModule
import com.koleychik.feature_image_info.di.modules.ViewModelModule
import com.koleychik.feature_image_info.ui.ImageInfoFragment
import dagger.Component

@Component(
    modules = [ImageInfoModule::class, ViewModelModule::class],
    dependencies = [ImageInfoFeatureDependencies::class]
)
interface ImageInfoFeatureComponent : ImageInfoFeatureApi {

    fun inject(fragment: ImageInfoFragment)

    companion object {
        fun initAndGet(dependencies: ImageInfoFeatureDependencies): ImageInfoFeatureComponent =
            DaggerImageInfoFeatureComponent.builder().imageInfoFeatureDependencies(dependencies)
                .build()
    }

}