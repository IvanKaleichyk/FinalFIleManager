package com.koleychik.core_files.api

import com.koleychik.core_files.FilesCoreConstants.ROOT_PATH
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.MusicModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel

interface FilesRepository {

    fun getImages(): List<ImageModel>

    fun getDocuments(): List<DocumentModel>

    fun getMusic(): List<MusicModel>

    fun getVideo(): List<VideoModel>

    fun getFoldersAndFiles(path: String = ROOT_PATH): List<FileCarcass>

    fun gelFilesFromFolder(path: String = ROOT_PATH): List<FileCarcass>

    fun delete(model: FileCarcass)
}