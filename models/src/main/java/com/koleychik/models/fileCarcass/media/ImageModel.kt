package com.koleychik.models.fileCarcass.media

import android.net.Uri
import com.koleychik.models.type.FileType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    override val id: Long,
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
) : MediaCarcass(id, name, uri, sizeAbbreviation, dateAdded, FileType.ImageType)
