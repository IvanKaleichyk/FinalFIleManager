package com.koleychik.feature_folders_and_files.di

import androidx.lifecycle.ViewModel
import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_folders_and_files.ui.viewModel.FoldersAndFilesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FoldersAndFilesViewModel::class)
    abstract fun provideViewModel(viewModel: FoldersAndFilesViewModel): ViewModel

}