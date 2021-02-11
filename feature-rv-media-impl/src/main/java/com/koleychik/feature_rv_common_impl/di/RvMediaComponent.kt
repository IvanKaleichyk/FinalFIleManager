package com.koleychik.feature_rv_common_impl.di

import com.koleychik.feature_rv_common_api.api.RvMediaApi
import dagger.Component

@Component(modules = [RvMediaModule::class])
interface RvMediaComponent : RvMediaApi {

    companion object {
        fun initAndGet(): RvMediaComponent = DaggerRvMediaComponent.builder().build()
    }

}