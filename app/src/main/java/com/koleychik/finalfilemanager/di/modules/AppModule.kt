package com.koleychik.finalfilemanager.di.modules

import android.content.Context
import com.koleychik.feature_documents.di.DocumentsFeatureApi
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureApi
import com.koleychik.feature_image_info.di.ImageInfoFeatureApi
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_music.di.MusicFeatureApi
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_video.di.VideoFeatureApi
import com.koleychik.finalfilemanager.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext() = context

    @Provides
    @Singleton
    fun provideNavigator(
        imagesFeatureApi: Provider<ImagesFeatureApi>,
        selectCategoryApi: Provider<SelectCategoryApi>,
        musicFeatureApi: Provider<MusicFeatureApi>,
        documentsFeatureApi: Provider<DocumentsFeatureApi>,
        videoFeatureApi: Provider<VideoFeatureApi>,
        imageInfoFeatureApi: Provider<ImageInfoFeatureApi>,
        foldersAndFilesFeatureApi: Provider<FoldersAndFilesFeatureApi>
    ) = Navigator(
        selectCategoryApi,
        imagesFeatureApi,
        musicFeatureApi,
        documentsFeatureApi,
        videoFeatureApi,
        imageInfoFeatureApi,
        foldersAndFilesFeatureApi
    )

}