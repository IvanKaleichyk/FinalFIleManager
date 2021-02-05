package com.koleychik.feature_loading_api

import com.koleychik.module_injector.component_holder.BaseApi

interface FeatureLoadingApi : BaseApi {

    fun getLoadingApi(): LoadingApi

}