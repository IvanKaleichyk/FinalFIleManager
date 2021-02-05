package com.koleychik.feature_select_category.navigation

import com.koleychik.module_injector.navigation.NavigatorComponentHolder

object SelectCategoryNavigationComponentHolder :
    NavigatorComponentHolder<SelectCategoryNavigationApi> {

    private var api: SelectCategoryNavigationApi? = null

    override fun init(api: SelectCategoryNavigationApi) {
        this.api = api
    }

    override fun get(): SelectCategoryNavigationApi = api!!

    override fun reset() {
        api = null
    }
}