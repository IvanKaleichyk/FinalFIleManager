package com.koleychik.core_files.di.modules

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.core_files.FilesRepositoryImpl
import com.koleychik.core_files.api.FilesRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class FileCoreModule {

    @Binds
    @PerFeature
    abstract fun provideFileRepository(repository: FilesRepositoryImpl): FilesRepository

}