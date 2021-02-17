package com.koleychik.feature_searching_api

import com.koleychik.models.fileCarcass.MusicModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel
import com.koleychik.module_injector.component_holder.BaseApi

interface SearchingFeatureApi : BaseApi {

    fun searchingApiImageModel(): SearchingApi<ImageModel>
    fun searchingApiVideoModel(): SearchingApi<VideoModel>
    fun searchingApiMusicModel(): SearchingApi<MusicModel>
    fun searchingApiDocumentModel(): SearchingApi<DocumentModel>

    fun searchingUI(): SearchingUIApi

}