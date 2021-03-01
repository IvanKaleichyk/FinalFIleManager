package com.koleychik.models.fileCarcass.document

import android.net.Uri
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.type.FileType


data class DocumentModel(
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
    val format: DocumentType,
    override val type: FileType
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded, type)