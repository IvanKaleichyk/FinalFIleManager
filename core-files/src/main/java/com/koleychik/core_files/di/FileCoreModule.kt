package com.koleychik.core_files.di

import com.koleychik.core_files.FileRepositoryImpl
import com.koleychik.core_files.api.FilesRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class FileCoreModule {

    @Binds
    abstract fun provideFileRepository(repository: FileRepositoryImpl): FilesRepository

}