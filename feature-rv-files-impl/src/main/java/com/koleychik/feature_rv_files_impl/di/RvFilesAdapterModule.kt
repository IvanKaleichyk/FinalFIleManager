package com.koleychik.feature_rv_files_impl.di

import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterViewHolder
import com.koleychik.feature_rv_files_impl.framework.RvFilesAdapterImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class RvFilesAdapterModule {

    @Binds
    abstract fun provideRvFilesAdapter(impl: RvFilesAdapterImpl): RvFilesAdapterApi

    @Binds
    abstract fun provideViewHolder(impl: RvFilesAdapterImpl.MainViewHolder): RvFilesAdapterViewHolder

}