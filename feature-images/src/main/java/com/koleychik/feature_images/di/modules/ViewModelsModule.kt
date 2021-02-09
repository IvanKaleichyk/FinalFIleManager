package com.koleychik.feature_images.di.modules

import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_images.ui.viewModels.ImagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    abstract fun provideImagesViewModel(): ImagesViewModel

}