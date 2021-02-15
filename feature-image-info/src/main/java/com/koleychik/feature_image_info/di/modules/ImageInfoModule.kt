package com.koleychik.feature_image_info.di.modules

import com.koleychik.feature_image_info.ui.adapter.ImageInfoAdapter
import dagger.Module
import dagger.Provides

@Module
internal class ImageInfoModule {
    @Provides
    fun provideImageInfoAdapter() = ImageInfoAdapter()
}