package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicModel(
    override val name: String,
    val artist: String,
    val title: String,
    val album: String,
    val duration: Long,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long,
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded)