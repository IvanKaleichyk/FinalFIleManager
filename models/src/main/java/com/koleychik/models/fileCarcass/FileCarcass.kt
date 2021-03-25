package com.koleychik.models.fileCarcass

import android.net.Uri
import com.koleychik.models.getWeight
import com.koleychik.models.type.FileType


abstract class FileCarcass {
    val weight: Int by lazy {
        if (name.isEmpty()) this.getWeight(null)
        else this.getWeight(name[0])
    }

    abstract val name: String
    abstract val uri: Uri
    abstract val sizeAbbreviation: String?
    abstract val dateAdded: Long?
    abstract val type: FileType
    abstract val mimeType: String

    override fun toString(): String {
        return name
    }
}
