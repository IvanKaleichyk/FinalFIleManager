package com.koleychik.feature_searching_impl.di

import dagger.Component

@Component(modules = [SearchingFeatureModule::class])
interface SearchingFeatureComponent : SearchingFeatureApi {

    companion object {

        @Volatile
        private var instance: SearchingFeatureComponent? = null

        fun get(): SearchingFeatureComponent {
            if (instance == null) synchronized(SearchingFeatureComponent::class.java) {
                if (instance == null) instance = DaggerSearchingFeatureComponent.builder().build()
            }
            return instance!!
        }
    }

}