package com.koleychik.feature_searching_impl.di

import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.feature_searching_impl.framework.SearchingUIImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class SearchingFeatureModule {

    @Binds
    abstract fun provideSearchingUIApi(impl: SearchingUIImpl): SearchingUIApi

}