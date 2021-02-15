package com.koleychik.feature_video.di

import com.koleychik.module_injector.component_holder.BaseDestroyer

interface VideoFeatureDestroyer : BaseDestroyer {
    fun destroy()
}