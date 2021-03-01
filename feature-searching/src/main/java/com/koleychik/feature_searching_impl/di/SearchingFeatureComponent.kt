package com.koleychik.feature_searching_impl.di

import dagger.Component

@Component(modules = [SearchingFeatureModule::class])
interface SearchingFeatureComponent : SearchingFeatureApi {

    companion object {
        fun initAndGet(): SearchingFeatureComponent =
            DaggerSearchingFeatureComponent.builder().build()
    }

}