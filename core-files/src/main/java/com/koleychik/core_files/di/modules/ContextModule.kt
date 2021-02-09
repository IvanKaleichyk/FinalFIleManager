package com.koleychik.core_files.di.modules

import android.content.Context
import com.koleychik.app_annotations.general.PerFeature
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @PerFeature
    fun provideContext() = context

}