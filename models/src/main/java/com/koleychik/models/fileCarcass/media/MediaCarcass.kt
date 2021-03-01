package com.koleychik.models.fileCarcass.media

import android.net.Uri
import android.os.Parcelable
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.type.FileType

abstract class MediaCarcass(
    open val id: Long,
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long?,
    override val type: FileType
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded, type), Parcelable