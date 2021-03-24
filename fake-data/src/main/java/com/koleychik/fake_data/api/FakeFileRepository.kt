package com.koleychik.fake_data.api

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.fake_data.data.FakeImagesData
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.MusicModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.media.ImageModel
import com.koleychik.models.fileCarcass.media.VideoModel

class FakeFileRepository : FilesRepository {

    private val fakeImagesData = FakeImagesData()

    override fun getImages(): List<ImageModel> = fakeImagesData.getList()

    override fun getDocuments(): List<DocumentModel> {
        TODO("Not yet implemented")
    }

    override fun getMusic(): List<MusicModel> {
        TODO("Not yet implemented")
    }

    override fun getVideo(): List<VideoModel> {
        TODO("Not yet implemented")
    }

    override fun getFoldersAndFiles(path: String): List<FileCarcass> {
        TODO("Not yet implemented")
    }

    override fun openFile(model: FileCarcass) {
        TODO("Not yet implemented")
    }


    override fun delete(model: FileCarcass) {
        TODO("Not yet implemented")
    }
}