package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.getWeight
import com.koleychik.models.type.FileType


abstract class FileCarcass(
//    open val id: Long,
    open val name: String,
    open val uri: Uri,
    open val sizeAbbreviation: String?,
    open val dateAdded: Long?,
    open val type: FileType
) {
    val weight: Int by lazy { this.getWeight(name[0]) }

    override fun toString(): String {
        return name
    }
}
