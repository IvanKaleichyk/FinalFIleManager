package com.koleychik.feature_documents.di

import com.koleychik.module_injector.component_holder.ComponentHolder

object DocumentsFeatureComponentHolder :
    ComponentHolder<DocumentsFeatureApi, DocumentsFeatureDependencies, DocumentFeatureDestroyer> {

    @Volatile
    private var component: DocumentsFeatureComponent? = null

    private lateinit var destroyer: DocumentFeatureDestroyer

    override fun init(
        dependencies: DocumentsFeatureDependencies,
        destroyer: DocumentFeatureDestroyer
    ) {
        if (component == null) synchronized(DocumentsFeatureComponentHolder::class) {
            if (component == null) component = DocumentsFeatureComponent.initAndGet(dependencies)
        }
        this.destroyer = destroyer
    }

    internal fun getComponent() = component!!

    override fun get(): DocumentsFeatureApi = component!!

    override fun reset() {
        component = null
        destroyer.destroy()
    }
}