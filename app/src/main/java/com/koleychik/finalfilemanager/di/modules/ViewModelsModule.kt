package com.koleychik.finalfilemanager.di.modules

import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_images.ui.viewModels.ImagesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    @Binds
    abstract fun provideImagesViewModel()

}