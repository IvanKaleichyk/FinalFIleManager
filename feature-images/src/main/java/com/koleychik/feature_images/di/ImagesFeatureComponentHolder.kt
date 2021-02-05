package com.koleychik.feature_images.di

import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.module_injector.component_holder.ComponentHolder

object ImagesFeatureComponentHolder : ComponentHolder<ImagesFeatureApi, ImagesFeatureDependencies> {

    @Volatile
    private var component: ImagesFeatureComponent? = null

    internal fun getComponent() = component!!

    override fun init(dependency: ImagesFeatureDependencies) {
        if (component == null) synchronized(ImagesFeatureComponentHolder::class) {
            if (component == null) component = ImagesFeatureComponent.initAndGet(dependency)
        }
    }

    override fun get(): ImagesFeatureApi = component!!

    override fun reset() {
       component = null
    }
}