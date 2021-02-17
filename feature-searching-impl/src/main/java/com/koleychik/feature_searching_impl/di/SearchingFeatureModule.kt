package com.koleychik.feature_searching_impl.di

import com.koleychik.app_annotations.general.PerFeature
import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.feature_searching_api.SearchingUIApi
import com.koleychik.feature_searching_impl.framework.SearchingImpl
import com.koleychik.feature_searching_impl.framework.SearchingUIImpl
import com.koleychik.models.fileCarcass.MusicModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel
import dagger.Binds
import dagger.Module

@Module
internal abstract class SearchingFeatureModule {

    @PerFeature
    @Binds
    abstract fun provideSearchingUIApi(impl: SearchingUIImpl): SearchingUIApi

    @Binds
    abstract fun provideSearchingApiImageModel(impl: SearchingImpl<ImageModel>): SearchingApi<ImageModel>

    @Binds
    abstract fun provideSearchingApiVideoModel(impl: SearchingImpl<VideoModel>): SearchingApi<VideoModel>

    @Binds
    abstract fun provideSearchingApiMusicModel(impl: SearchingImpl<MusicModel>): SearchingApi<MusicModel>

    @Binds
    abstract fun provideSearchingApiDocumentModel(impl: SearchingImpl<DocumentModel>): SearchingApi<DocumentModel>

}