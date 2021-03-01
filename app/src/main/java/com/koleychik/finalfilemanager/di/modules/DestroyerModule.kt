package com.koleychik.finalfilemanager.di.modules

import com.koleychik.feature_documents.di.DocumentFeatureDestroyer
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureComponentHolder
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureDestroyer
import com.koleychik.feature_images.ImagesFeatureDestroyer
import com.koleychik.feature_loading_impl.di.LoadingComponentHolder
import com.koleychik.feature_music.di.MusicFeatureDestroyer
import com.koleychik.feature_rv_common_impl.di.RvMediaComponentHolder
import com.koleychik.feature_rv_files_impl.di.RvFilesAdapterComponentHolder
import com.koleychik.feature_video.di.VideoFeatureDestroyer
import com.koleychik.module_injector.component_holder.BaseDestroyer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DestroyerModule {

    @Provides
    @Singleton
    fun provideFoldersAndFilesFeatureDestroyer() = object : FoldersAndFilesFeatureDestroyer {
        override fun destroy() {
            LoadingComponentHolder.reset()
            RvFilesAdapterComponentHolder.reset()
            FoldersAndFilesFeatureComponentHolder.reset()
            RvFilesAdapterComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideDocumentFeatureDestroyer() = object : DocumentFeatureDestroyer {
        override fun destroy() {
            LoadingComponentHolder.reset()
            RvFilesAdapterComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideVideoFeatureDestroyer() = object : VideoFeatureDestroyer {
        override fun destroy() {
            LoadingComponentHolder.reset()
            RvMediaComponentHolder.reset()
        }

    }

    @Provides
    @Singleton
    fun provideMusicFeatureDestroyer() = object : MusicFeatureDestroyer {
        override fun destroy() {
            LoadingComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun providesImagesFeatureDestroyer() = object : ImagesFeatureDestroyer {
        override fun destroy() {
            LoadingComponentHolder.reset()
            RvMediaComponentHolder.reset()
        }
    }

    @Provides
    @Singleton
    fun provideBaseDestroyer() = object : BaseDestroyer {}

}