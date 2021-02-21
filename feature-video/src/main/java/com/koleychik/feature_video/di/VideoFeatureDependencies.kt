package com.koleychik.feature_video.di

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface VideoFeatureDependencies : BaseDependencies {
    fun repository(): FilesRepository
    fun loadingApi(): LoadingApi
    fun mediaAdapter(): RvMediaAdapterApi
    fun searchingUiApi(): SearchingUIApi
}