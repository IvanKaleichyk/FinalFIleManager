package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_loading_impl.framework.LoadingImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class LoadingModule {

    @Binds
    abstract fun provideLoadingApi(impl: LoadingImpl): LoadingApi

}