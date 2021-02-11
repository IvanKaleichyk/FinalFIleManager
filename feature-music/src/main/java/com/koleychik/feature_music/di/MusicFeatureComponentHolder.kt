package com.koleychik.feature_music.di

import com.koleychik.module_injector.component_holder.ComponentHolder

object MusicFeatureComponentHolder :
    ComponentHolder<MusicFeatureApi, MusicFeatureDependencies, MusicFeatureDestroyer> {

    @Volatile
    private var component: MusicFeatureComponent? = null

    private lateinit var destroyer: MusicFeatureDestroyer

    internal fun getComponent() = component!!

    override fun init(dependencies: MusicFeatureDependencies, destroyer: MusicFeatureDestroyer) {
        if (component == null) synchronized(MusicFeatureComponentHolder::class.java) {
            if (component == null) component = MusicFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): MusicFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer.destroy()
    }
}