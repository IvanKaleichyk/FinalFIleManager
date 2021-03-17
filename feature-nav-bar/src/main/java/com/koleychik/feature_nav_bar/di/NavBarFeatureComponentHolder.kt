package com.koleychik.feature_nav_bar.di

import com.koleychik.module_injector.component_holder.BaseDestroyer
import com.koleychik.module_injector.component_holder.ComponentHolder

object NavBarFeatureComponentHolder :
    ComponentHolder<NavBarFeatureApi, NavBarFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: NavBarFeatureComponent? = null

    override fun init(dependencies: NavBarFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(NavBarFeatureComponentHolder::class.java) {
            if (component == null) component = NavBarFeatureComponent.initAndGet(dependencies)
        }
    }

    internal fun getComponent() = component!!

    override fun get(): NavBarFeatureApi = component!!

    override fun reset() {
        component = null
    }
}