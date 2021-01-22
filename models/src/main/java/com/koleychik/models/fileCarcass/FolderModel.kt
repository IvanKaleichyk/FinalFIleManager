package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class FolderModel(
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
    override val imgRes: Int = R.drawable.file_icon_48_black
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded, imgRes) {
}
