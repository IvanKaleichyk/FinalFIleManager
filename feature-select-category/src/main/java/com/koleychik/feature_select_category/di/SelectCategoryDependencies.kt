package com.koleychik.feature_select_category.di

import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface SelectCategoryDependencies : BaseDependencies{
    fun selectCategoryNavigationApi(): SelectCategoryNavigationApi
}