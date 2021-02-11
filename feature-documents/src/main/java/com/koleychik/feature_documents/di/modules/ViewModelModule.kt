package com.koleychik.feature_documents.di.modules

import androidx.lifecycle.ViewModel
import com.koleychik.app_annotations.ViewModelKey
import com.koleychik.feature_documents.ui.viewModels.DocumentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DocumentsViewModel::class)
    abstract fun provideDocumentsViewModel(viewModel: DocumentsViewModel): ViewModel

}