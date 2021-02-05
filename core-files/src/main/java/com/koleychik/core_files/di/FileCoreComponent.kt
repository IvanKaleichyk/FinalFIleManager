package com.koleychik.core_files.di

import com.koleychik.core_files.api.FilesRepository
import dagger.Component

@Component(modules = [FileCoreModule::class])
interface FileCoreComponent : FilesRepository {

    companion object {
        @Volatile
        var instance: FileCoreComponent? = null

        fun get(): FileCoreComponent {
            if (instance == null) synchronized(FileCoreComponent::class.java) {
                if (instance == null) instance = DaggerFileCoreComponent.builder().build()
            }
            return instance!!
        }
    }

}