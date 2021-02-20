package com.koleychik.feature_searching_impl.di

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.feature_searching_api.SearchingUIApi
import com.koleychik.feature_searching_impl.framework.SearchingImpl
import com.koleychik.feature_searching_impl.framework.SearchingUIImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class SearchingFeatureModule {

    @PerFeature
    @Binds
    abstract fun provideSearchingUIApi(impl: SearchingUIImpl): SearchingUIApi

    @Binds
    abstract fun provideSearchingApi(impl: SearchingImpl): SearchingApi

}