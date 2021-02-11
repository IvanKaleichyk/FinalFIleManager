package com.koleychik.feature_select_category.di

import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.module_injector.component_holder.BaseDestroyer
import com.koleychik.module_injector.component_holder.ComponentHolder

object SelectCategoryComponentHolder :
    ComponentHolder<SelectCategoryApi, SelectCategoryDependencies, BaseDestroyer> {

    @Volatile
    private var component: SelectCategoryComponent? = null
        get() = field!!

    internal fun getComponent() = component!!

    override fun init(dependencies: SelectCategoryDependencies, destroyer: BaseDestroyer) {
        component = SelectCategoryComponent.initAndGet(dependencies)
    }

    override fun get(): SelectCategoryApi = component!!

    override fun reset() {
        component = null
    }

}