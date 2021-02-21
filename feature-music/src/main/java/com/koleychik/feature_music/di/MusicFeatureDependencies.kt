package com.koleychik.feature_music.di

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface MusicFeatureDependencies : BaseDependencies {

    fun filesRepository(): FilesRepository
    fun loadingApi(): LoadingApi
    fun searchingUIApi(): SearchingUIApi

}