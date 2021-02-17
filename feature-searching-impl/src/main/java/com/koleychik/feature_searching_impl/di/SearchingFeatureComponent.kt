package com.koleychik.feature_searching_impl.di

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.feature_searching_api.SearchingFeatureApi
import dagger.Component

@Component(modules = [SearchingFeatureModule::class])
@PerFeature
interface SearchingFeatureComponent : SearchingFeatureApi {

    companion object {
        fun initAndGet() = DaggerSearchingFeatureComponent.builder().build()
    }

}