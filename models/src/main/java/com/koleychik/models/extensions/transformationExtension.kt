package com.koleychik.models.extensions

import android.content.Context
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.documentfile.provider.DocumentFile
import com.koleychik.models.fileCarcass.FolderModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.getTypeOfDocument
import com.koleychik.models.type.getFileType

fun DocumentFile.toDocumentModel(context: Context): DocumentModel {
    val rootName = name ?: ""
    val mimeType = type ?: ""
    return DocumentModel(
        name = rootName,
        uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            uri.toFile()
        ),
        sizeAbbreviation = context.getSizeAbbreviation(length()),
        dateAdded = null,
        format = getTypeOfDocument(rootName),
        type = getFileType(mimeType),
        mimeType = mimeType
    )
}

fun DocumentFile.toFolderModel() = FolderModel(
    name = name ?: "",
    uri = uri,
    dateAdded = null,
    mimeType = type ?: ""
)