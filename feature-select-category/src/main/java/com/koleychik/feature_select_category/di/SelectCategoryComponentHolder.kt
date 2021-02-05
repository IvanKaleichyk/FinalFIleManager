package com.koleychik.feature_select_category.di

import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.module_injector.component_holder.ComponentHolder

object SelectCategoryComponentHolder :
    ComponentHolder<SelectCategoryApi, SelectCategoryDependencies> {

    @Volatile
    private var component: SelectCategoryComponent? = null
        get() = field!!

    internal fun getComponent() = component!!

    override fun init(dependency: SelectCategoryDependencies) {
        component = SelectCategoryComponent.initAndGet(dependency)
    }

    override fun get(): SelectCategoryApi = component!!

    override fun reset() {
        component = null
    }

}