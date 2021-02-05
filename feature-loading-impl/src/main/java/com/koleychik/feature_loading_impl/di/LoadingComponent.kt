package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.FeatureLoadingApi
import dagger.Component

@Component(modules = [LoadingModule::class])
interface LoadingComponent : FeatureLoadingApi {

    companion object{
        fun initAndGet(): LoadingComponent = DaggerLoadingComponent.builder().build()
    }

}