package com.koleychik.feature_video.di

import com.koleychik.module_injector.component_holder.ComponentHolder

object VideoFeatureComponentHolder :
    ComponentHolder<VideoFeatureApi, VideoFeatureDependencies, VideoFeatureDestroyer> {

    @Volatile
    private var component: VideoFeatureComponent? = null

    private lateinit var destroyer: VideoFeatureDestroyer

    internal fun getComponent() = component!!

    override fun init(dependencies: VideoFeatureDependencies, destroyer: VideoFeatureDestroyer) {
        if (component == null) synchronized(VideoFeatureComponentHolder::class.java) {
            if (component == null) component = VideoFeatureComponent.initAndGet(dependencies)
        }
    }

    override fun get(): VideoFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer.destroy()
    }

}