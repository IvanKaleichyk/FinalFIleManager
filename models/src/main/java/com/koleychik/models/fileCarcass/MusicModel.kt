package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.type.FileType

data class MusicModel(
    val id: Long,
    override val name: String,
    val artist: String,
    val title: String,
    val album: String,
    val duration: Long,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long,
    override val mimeType: String,
) : FileCarcass() {
    override val type: FileType = FileType.AudioType
}