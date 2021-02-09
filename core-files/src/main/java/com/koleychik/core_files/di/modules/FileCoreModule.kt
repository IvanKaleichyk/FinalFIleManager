package com.koleychik.core_files.di.modules

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.core_files.FileRepositoryImpl
import com.koleychik.core_files.api.FilesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class FileCoreModule {

    @Binds
    @PerFeature
    abstract fun provideFileRepository(repository: FileRepositoryImpl): FilesRepository

}