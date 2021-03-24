package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.type.FileType


data class FolderModel(
    override val name: String,
    override val uri: Uri,
    override val dateAdded: Long?,
    override val mimeType: String,
) : FileCarcass() {
    override val sizeAbbreviation: String? = null
    override val type: FileType = FileType.FolderType
}