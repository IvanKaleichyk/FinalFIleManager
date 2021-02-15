package com.koleychik.feature_video.di

import com.koleychik.feature_video.ui.VideoFragment
import dagger.Component

@Component(
    modules = [VideoFeatureModule::class],
    dependencies = [VideoFeatureDependencies::class]
)
interface VideoFeatureComponent : VideoFeatureApi {
    fun inject(fragment: VideoFragment)

    companion object {
        fun initAndGet(dependencies: VideoFeatureDependencies): VideoFeatureComponent =
            DaggerVideoFeatureComponent.builder().videoFeatureDependencies(dependencies).build()
    }

}