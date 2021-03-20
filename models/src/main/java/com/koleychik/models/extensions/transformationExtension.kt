package com.koleychik.models.extensions

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import com.koleychik.models.fileCarcass.FolderModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.getTypeOfDocument
import com.koleychik.models.type.getFileType

fun DocumentFile.toDocumentModel(context: Context): DocumentModel {
    val rootName = name ?: ""
    return DocumentModel(
        name = rootName,
        uri = uri,
        sizeAbbreviation = context.getSizeAbbreviation(length()),
        dateAdded = null,
        format = getTypeOfDocument(rootName),
        type = getFileType(type ?: "")
    )
}

fun DocumentFile.toFolderModel() = FolderModel(
    name = name ?: "",
    uri = uri,
    dateAdded = null
)