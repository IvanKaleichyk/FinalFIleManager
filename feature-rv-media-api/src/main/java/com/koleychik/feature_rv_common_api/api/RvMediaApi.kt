package com.koleychik.feature_rv_common_api.api

import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.module_injector.component_holder.BaseApi


interface RvMediaApi : BaseApi {
    fun rvMediaAdapterApi(): RvMediaAdapterApi
}