package com.koleychik.models.extensions

import android.content.Context
import androidx.core.net.toUri
import com.koleychik.models.fileCarcass.FolderModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.getTypeOfDocument
import java.io.File

fun File.toDocumentModel(context: Context) = DocumentModel(
    name = name,
    uri = toUri(),
    sizeAbbreviation = context.getSizeAbbreviation(length()),
    dateAdded = null,
    format = getTypeOfDocument(name)
)

fun File.toFolderModel(context: Context) = FolderModel(
    name,
    toUri(),
    context.getSizeAbbreviation(length()),
    null
)