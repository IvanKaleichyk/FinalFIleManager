package com.koleychik.feature_searching_impl.di

import com.koleychik.feature_searching_api.SearchingFeatureApi
import com.koleychik.module_injector.component_holder.BaseDependencies
import com.koleychik.module_injector.component_holder.BaseDestroyer
import com.koleychik.module_injector.component_holder.ComponentHolder

object SearchingFeatureComponentHolder :
    ComponentHolder<SearchingFeatureApi, BaseDependencies, BaseDestroyer> {

    @Volatile
    private var component: SearchingFeatureComponent? = null

    override fun init(dependencies: BaseDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(SearchingFeatureComponentHolder::class.java) {
            if (component == null) component = SearchingFeatureComponent.initAndGet()
        }
    }

    override fun get(): SearchingFeatureApi = component!!

    override fun reset() {
        component = null
    }
}