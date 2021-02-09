package com.koleychik.feature_images.di.modules

import androidx.lifecycle.ViewModel
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
    abstract fun provideImagesViewModel(viewModel: ImagesViewModel): ViewModel

}