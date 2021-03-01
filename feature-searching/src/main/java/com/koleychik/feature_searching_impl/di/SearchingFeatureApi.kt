package com.koleychik.feature_searching_impl.di

import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.module_injector.component_holder.BaseApi

interface SearchingFeatureApi : BaseApi {
    fun searchingUIApi(): SearchingUIApi
}
