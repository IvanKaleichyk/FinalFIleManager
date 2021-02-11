package com.koleychik.feature_music.di

import com.koleychik.module_injector.component_holder.BaseDestroyer

interface MusicFeatureDestroyer: BaseDestroyer {

    fun destroy()

}