package com.koleychik.feature_music.di.modules

import com.koleychik.feature_music.adapters.MusicAdapter
import dagger.Module
import dagger.Provides

@Module
class MusicFeatureModule {

    @Provides
    fun provideMusicAdapter() = MusicAdapter()

}