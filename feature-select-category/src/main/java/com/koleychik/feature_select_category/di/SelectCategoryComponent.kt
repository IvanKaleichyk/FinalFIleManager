package com.koleychik.feature_select_category.di

import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_select_category.ui.SelectCategoryFragment
import dagger.Component

@Component(
    modules = [SelectCategoryFeatureModule::class],
    dependencies = [SelectCategoryDependencies::class]
)
interface SelectCategoryComponent : SelectCategoryApi {

    fun inject(fragment: SelectCategoryFragment)

    companion object {
        fun initAndGet(dependencies: SelectCategoryDependencies): SelectCategoryComponent =
            DaggerSelectCategoryComponent.builder().selectCategoryDependencies(dependencies).build()
    }

}