package com.koleychik.feature_documents.di

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface DocumentsFeatureDependencies : BaseDependencies {

    fun repository(): FilesRepository
    fun loadingApi(): LoadingApi
    fun rvFilesAdapterApi(): RvFilesAdapterApi
    fun searchingUIApi(): SearchingUIApi

}