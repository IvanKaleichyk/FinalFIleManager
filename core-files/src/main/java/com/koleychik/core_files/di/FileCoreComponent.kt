package com.koleychik.core_files.di

import android.content.Context
import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.core_files.api.FileCoreApi
import com.koleychik.core_files.di.modules.ContextModule
import com.koleychik.core_files.di.modules.FileCoreModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FileCoreModule::class, ContextModule::class])
@PerFeature
interface FileCoreComponent : FileCoreApi {

    companion object {
        @Volatile
        var instance: FileCoreComponent? = null

        fun get(context: Context): FileCoreComponent {
            if (instance == null) synchronized(FileCoreComponent::class.java) {
                if (instance == null) instance =
                    DaggerFileCoreComponent.builder().contextModule(ContextModule(context)).build()
            }
            return instance!!
        }
    }

}