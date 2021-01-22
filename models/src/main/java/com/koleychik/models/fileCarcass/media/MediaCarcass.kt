package com.koleychik.models.fileCarcass.media

import android.net.Uri
import com.koleychik.models.fileCarcass.FileCarcass

abstract class MediaCarcass(
    open val id: Long,
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
    override val imgRes: Int
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded, imgRes)