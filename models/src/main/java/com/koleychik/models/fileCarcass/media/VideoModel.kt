package com.koleychik.models.fileCarcass.media

import android.net.Uri
import com.koleychik.models.type.FileType
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class VideoModel(
    override val id: Long,
    override val name: String,
    override val uri: Uri,
    val duration: Long,
    override val sizeAbbreviation: String,
    override val dateAdded: Long,
    override val mimeType: String,
) : MediaCarcass(id) {
    @IgnoredOnParcel
    override val type: FileType = FileType.VideoType
}