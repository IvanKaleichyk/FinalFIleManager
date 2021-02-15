package com.koleychik.feature_video.di

import androidx.lifecycle.ViewModel
import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_video.ui.viewModel.VideoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class VideoFeatureModule {

    @Binds
    @ViewModelKey(VideoViewModel::class)
    @IntoMap
    abstract fun provideVideoModel(viewModel: VideoViewModel): ViewModel

}