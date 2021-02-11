package com.koleychik.feature_music.di

import com.koleychik.feature_music.MusicFragment
import com.koleychik.feature_music.di.modules.MusicFeatureModule
import com.koleychik.feature_music.di.modules.ViewModelModule
import dagger.Component

@Component(
    modules = [MusicFeatureModule::class, ViewModelModule::class],
    dependencies = [MusicFeatureDependencies::class]
)
interface MusicFeatureComponent : MusicFeatureApi {

    fun inject(fragment: MusicFragment)

    companion object {
        fun initAndGet(dependencies: MusicFeatureDependencies): MusicFeatureComponent =
            DaggerMusicFeatureComponent.builder().musicFeatureDependencies(dependencies).build()
    }

}