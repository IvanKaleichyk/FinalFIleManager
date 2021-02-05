package com.koleychik.feature_loading_impl.di

import com.koleychik.feature_loading_api.FeatureLoadingApi
import com.koleychik.module_injector.component_holder.BaseDependencies
import com.koleychik.module_injector.component_holder.ComponentHolder

object LoadingComponentHolder : ComponentHolder<FeatureLoadingApi, BaseDependencies> {

    private var component: LoadingComponent? = null

    override fun init(dependency: BaseDependencies) {
        component = LoadingComponent.initAndGet()
    }

    override fun get(): FeatureLoadingApi = component!!

    override fun reset() {
        component = null
    }
}