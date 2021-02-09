package com.koleychik.feature_images.di

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface ImagesFeatureDependencies : BaseDependencies {
    fun repository(): FilesRepository
    fun navigator(): ImagesFeatureNavigationApi
    fun rvMediaAdapterApi(): RvMediaAdapterApi
    fun loadingApi(): LoadingApi
}