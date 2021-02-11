package com.koleychik.feature_images.di

import com.koleychik.feature_images.ImagesFeatureDestroyer
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.module_injector.component_holder.ComponentHolder

object ImagesFeatureComponentHolder :
    ComponentHolder<ImagesFeatureApi, ImagesFeatureDependencies, ImagesFeatureDestroyer> {

    @Volatile
    private var component: ImagesFeatureComponent? = null

    internal fun getComponent() = component!!

    private lateinit var destroyer: ImagesFeatureDestroyer

    override fun init(dependencies: ImagesFeatureDependencies, destroyer: ImagesFeatureDestroyer) {
        if (component == null) synchronized(ImagesFeatureComponentHolder::class) {
            if (component == null) component = ImagesFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): ImagesFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer.destroy()
    }
}