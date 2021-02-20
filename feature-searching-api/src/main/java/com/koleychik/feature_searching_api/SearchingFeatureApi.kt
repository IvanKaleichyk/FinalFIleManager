package com.koleychik.feature_searching_api

import com.koleychik.module_injector.component_holder.BaseApi

interface SearchingFeatureApi : BaseApi {

    fun searchingApi(): SearchingApi
    fun searchingUIApi(): SearchingUIApi

}