package com.koleychik.feature_music.di

import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.module_injector.component_holder.BaseDependencies

interface MusicFeatureDependencies : BaseDependencies {

    fun loadingApi(): LoadingApi

}