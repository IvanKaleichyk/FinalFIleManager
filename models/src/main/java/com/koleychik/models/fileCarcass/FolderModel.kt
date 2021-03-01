package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.type.FileType


data class FolderModel(
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded, FileType.FolderType)