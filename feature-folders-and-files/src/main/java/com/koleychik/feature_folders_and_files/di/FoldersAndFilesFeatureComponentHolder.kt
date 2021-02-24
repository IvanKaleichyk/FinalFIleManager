package com.koleychik.feature_folders_and_files.di

import com.koleychik.module_injector.component_holder.ComponentHolder

object FoldersAndFilesFeatureComponentHolder :
    ComponentHolder<FoldersAndFilesFeatureApi, FoldersAndFilesFeatureDependencies, FoldersAndFilesFeatureDestroyer> {

    @Volatile
    private var component: FoldersAndFilesFeatureComponent? = null
    private lateinit var destroyer: FoldersAndFilesFeatureDestroyer

    fun getComponent() = component!!

    override fun init(
        dependencies: FoldersAndFilesFeatureDependencies,
        destroyer: FoldersAndFilesFeatureDestroyer
    ) {
        if (component == null) synchronized(FoldersAndFilesFeatureComponentHolder::class.java) {
            if (component == null) component =
                FoldersAndFilesFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    override fun get(): FoldersAndFilesFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer.destroy()
    }
}