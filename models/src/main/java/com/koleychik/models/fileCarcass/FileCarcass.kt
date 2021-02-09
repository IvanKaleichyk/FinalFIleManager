package com.koleychik.models.fileCarcass

import android.net.Uri
import android.os.Parcelable
import com.koleychik.models.getWeight


abstract class FileCarcass(
//    open val id: Long,
    open val name: String,
    open val uri: Uri,
    open val sizeAbbreviation: String,
    open val dateAdded: Long?
) : Parcelable {
    val weight: Int by lazy { this.getWeight(name[0]) }


}
