package com.koleychik.feature_rv_common_impl.di

import com.koleychik.feature_rv_common_api.RvMediaApi
import com.koleychik.module_injector.component_holder.BaseDependencies
import com.koleychik.module_injector.component_holder.ComponentHolder

object RvMediaComponentHolder : ComponentHolder<RvMediaApi, BaseDependencies> {

    private var component: RvMediaComponent? = null

    override fun init(dependency: BaseDependencies) {
        component = RvMediaComponent.initAndGet()
    }

    override fun get(): RvMediaApi = component!!

    override fun reset() {
        component = null
    }


}