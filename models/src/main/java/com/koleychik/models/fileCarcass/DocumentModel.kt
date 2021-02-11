package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentModel(
    override val name: String,
    override val uri: Uri,
    override val sizeAbbreviation: String,
    override val dateAdded: Long,
    val imgRes: Int = R.drawable.documents_icon_48_black
) : FileCarcass(name, uri, sizeAbbreviation, dateAdded)