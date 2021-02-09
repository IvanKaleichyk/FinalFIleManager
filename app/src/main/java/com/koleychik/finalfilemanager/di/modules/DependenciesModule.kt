package com.koleychik.finalfilemanager.di.modules

import android.content.Context
import com.koleychik.core_files.api.FileCoreApi
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.core_files.di.FileCoreComponent
import com.koleychik.feature_images.di.ImagesFeatureDependencies
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_loading_api.FeatureLoadingApi
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_rv_common_api.RvMediaApi
import com.koleychik.feature_select_category.di.SelectCategoryDependencies
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationApi
import com.koleychik.finalfilemanager.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependenciesModule {

    @Provides
    @Singleton
    fun provideImagesFeatureDependencies(
        context: Context,
        navigator: Navigator,
        rvMediaApi: RvMediaApi,
        featureLoadingApi: FeatureLoadingApi
    ) = object : ImagesFeatureDependencies {
        override fun repository(): FilesRepository = FileCoreComponent.get(context).getFileRepository()
        override fun navigator(): ImagesFeatureNavigationApi = navigator
        override fun rvMediaAdapterApi(): RvMediaAdapterApi = rvMediaApi.rvMediaAdapterApi()
        override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()
    }

    @Provides
    @Singleton
    fun provideSelectCategoryDependencies(navigator: Navigator) =
        object : SelectCategoryDependencies {
            override fun selectCategoryNavigationApi(): SelectCategoryNavigationApi = navigator
        }
}