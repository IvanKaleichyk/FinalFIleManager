package com.koleychik.feature_rv_files_impl.di

import com.koleychik.feature_rv_documents_api.RvFilesApi
import com.koleychik.module_injector.component_holder.BaseDependencies
import com.koleychik.module_injector.component_holder.BaseDestroyer
import com.koleychik.module_injector.component_holder.ComponentHolder

object RvFilesAdapterComponentHolder :
    ComponentHolder<RvFilesApi, BaseDependencies, BaseDestroyer> {

    private var component: RvFilesAdapterComponent? = null

    override fun init(dependencies: BaseDependencies, destroyer: BaseDestroyer) {
        component = RvFilesAdapterComponent.initAndGet()
    }

    override fun get(): RvFilesApi = component!!

    override fun reset() {
        component = null
    }
}