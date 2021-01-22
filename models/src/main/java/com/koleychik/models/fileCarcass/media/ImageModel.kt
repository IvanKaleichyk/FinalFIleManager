package com.koleychik.models.fileCarcass.media

import android.net.Uri
import com.koleychik.models.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    override val id: Long,
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
    override val imgRes: Int = R.drawable.images_icon_48_black
) : MediaCarcass(id, name, uri, sizeAbbreviation, dateAdded, imgRes)
