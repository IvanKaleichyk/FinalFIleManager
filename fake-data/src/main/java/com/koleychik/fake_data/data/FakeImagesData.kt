package com.koleychik.fake_data.data

import android.net.Uri
import com.koleychik.models.fileCarcass.media.ImageModel
import java.util.*

class FakeImagesData {

    fun getList(): List<ImageModel> {
        val list = mutableListOf<ImageModel>()
//        val listUri = getListUri()
//        for (i in (listUri.indices)) list.add(
//            createModel(id = i.toLong(), name = "Name$i", listUri[i])
//        )
        return list
    }

    fun createModel(
        id: Long = 0,
        name: String = "",
        uri: Uri,
        sizeAbbreviation: String = "50 mb",
        dateAdded: Long = Date().time
    ) = ImageModel(id, name, uri, sizeAbbreviation, dateAdded)

//    fun getListUri() = listOf(
//
//    )

}