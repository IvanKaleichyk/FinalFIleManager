package com.koleychik.feature_searching_impl.framework

import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.DocumentType

fun <T : FileCarcass> List<T>.searchByName(value: String?): List<T> {
    return if (value == null || value == "") this
    else filter { it.name.contains(value, true) }
}

fun List<DocumentModel>.searchByType(listFormats: List<DocumentType>?): List<DocumentModel> {
    if (listFormats == null) return emptyList()
    val listRes = mutableListOf<DocumentModel>()
    for (model in this) {
        for (format in listFormats) if (model.format.id == format.id) {
            listRes.add(model)
            break
        }
    }
    return listRes
}