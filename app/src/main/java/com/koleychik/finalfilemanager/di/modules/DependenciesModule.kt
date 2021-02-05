package com.koleychik.finalfilemanager.di.modules

import com.koleychik.core_files.api.FileCoreApi
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_images.di.ImagesFeatureDependencies
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
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
        filesCoreApi: FileCoreApi,
        navigator: Navigator
    ) = object : ImagesFeatureDependencies {
        override fun repository(): FilesRepository = filesCoreApi.getFileRepository()

        override fun navigator(): ImagesFeatureNavigationApi = navigator
    }

    @Provides
    @Singleton
    fun provideSelectCategoryDependencies(navigator: Navigator) =
        object : SelectCategoryDependencies {
            override fun selectCategoryNavigationApi(): SelectCategoryNavigationApi = navigator
        }
}