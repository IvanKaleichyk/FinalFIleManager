package com.koleychik.core_files

import android.support.v4.media.MediaMetadataCompat
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.DocumentModel
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel

internal class FileRepositoryImpl : FilesRepository {
    override fun getImages(): List<ImageModel> {
        TODO("Not yet implemented")
    }

    override fun getDocuments(): List<DocumentModel> {
        TODO("Not yet implemented")
    }

    override fun getMusic(): List<MediaMetadataCompat> {
        TODO("Not yet implemented")
    }

    override fun getVideo(): List<VideoModel> {
        TODO("Not yet implemented")
    }

    override fun gelFilesFromFolder(path: String): List<FileCarcass> {
        TODO("Not yet implemented")
    }
}