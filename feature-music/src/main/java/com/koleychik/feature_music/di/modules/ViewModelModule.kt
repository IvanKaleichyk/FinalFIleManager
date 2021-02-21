package com.koleychik.feature_music.di.modules

import androidx.lifecycle.ViewModel
import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_music.ui.viewModel.MusicViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MusicViewModel::class)
    abstract fun provideMusicViewModel(viewModel: MusicViewModel): ViewModel

}