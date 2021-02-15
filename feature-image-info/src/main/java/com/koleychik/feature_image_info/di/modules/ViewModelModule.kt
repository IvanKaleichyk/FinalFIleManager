package com.koleychik.feature_image_info.di.modules

import androidx.lifecycle.ViewModel
import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_image_info.ui.viewModels.ImageInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(ImageInfoViewModel::class)
    abstract fun provideViewModel(viewModel: ImageInfoViewModel): ViewModel

}