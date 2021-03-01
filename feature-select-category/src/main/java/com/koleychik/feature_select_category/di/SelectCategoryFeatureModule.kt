package com.koleychik.feature_select_category.di

import com.koleychik.feature_select_category.SelectCategoryNavigationApi
import com.koleychik.feature_select_category.ui.SelectCategoryAdapter
import dagger.Module
import dagger.Provides

@Module
class SelectCategoryFeatureModule {

    @Provides
    fun provideAdapter(navigationApi: SelectCategoryNavigationApi) =
        SelectCategoryAdapter(navigationApi)

}