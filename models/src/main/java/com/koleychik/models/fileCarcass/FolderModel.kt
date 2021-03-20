package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.type.FileType


data class FolderModel(
    override val name: String,
    override val uri: Uri,
    override val dateAdded: Long?,
) : FileCarcass(name, uri, null, dateAdded, FileType.FolderType)