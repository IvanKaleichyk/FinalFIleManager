package com.koleychik.feature_image_info.di

import com.koleychik.module_injector.component_holder.BaseDestroyer
import com.koleychik.module_injector.component_holder.ComponentHolder

object ImageInfoFeatureComponentHolder :
    ComponentHolder<ImageInfoFeatureApi, ImageInfoFeatureDependencies, BaseDestroyer> {

    @Volatile
    private var component: ImageInfoFeatureComponent? = null

    override fun init(dependencies: ImageInfoFeatureDependencies, destroyer: BaseDestroyer) {
        if (component == null) synchronized(ImageInfoFeatureComponentHolder::class.java) {
            if (component == null) component = ImageInfoFeatureComponent.initAndGet(dependencies)
        }
    }

    internal fun getComponent() = component!!

    override fun get(): ImageInfoFeatureApi = component!!

    override fun reset() {
        component = null
    }
}