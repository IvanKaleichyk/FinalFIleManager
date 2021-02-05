package com.koleychik.core_files.api

import android.support.v4.media.MediaMetadataCompat
import com.koleychik.models.fileCarcass.DocumentModel
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel

interface FilesRepository {

    fun getImages(): List<ImageModel>

    fun getDocuments(): List<DocumentModel>

    fun getMusic(): List<MediaMetadataCompat>

    fun getVideo(): List<VideoModel>

    fun gelFilesFromFolder(path: String): List<FileCarcass>
}