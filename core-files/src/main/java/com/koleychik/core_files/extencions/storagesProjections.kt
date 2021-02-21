package com.koleychik.core_files.extencions

import android.content.Context
import com.koleychik.core_files.*
import java.math.BigDecimal

val imagesProjections = arrayOf(
    ImagesStorage._ID,
    ImagesStorage.DISPLAY_NAME,
    ImagesStorage.SIZE,
    ImagesStorage.DATE_ADDED
)

val audioProjections = arrayOf(
    AudioStorage._ID,
    AudioStorage.DISPLAY_NAME,
    AudioStorage.ARTIST,
    AudioStorage.TITLE,
    AudioStorage.ALBUM,
    AudioStorage.DURATION,
    AudioStorage.SIZE,
    AudioStorage.DATE_ADDED
)

val videoProjections = arrayOf(
    VideoStorage._ID,
    VideoStorage.DISPLAY_NAME,
    VideoStorage.DURATION,
    VideoStorage.SIZE,
    VideoStorage.DATE_ADDED
)

val documentsProjections = arrayOf(
    FilesStorage._ID,
    FilesStorage.DISPLAY_NAME,
    FilesStorage.SIZE,
    FilesStorage.DATE_ADDED
)

val allFilesFromFolderProjections = arrayOf(
    FilesStorage._ID,
    FilesStorage.DISPLAY_NAME,
    FilesStorage.RELATIVE_PATH
)

fun Context.getSizeAbbreviation( value: Long): String {
    val kb = 1024
    val mb = kb * 1024
    val gb = mb * 1024
    return when {
        value > kb -> "$value ${getString(R.string.bytes)}"
        value >= mb -> "${transform(value, kb)} ${getString(R.string.kb)}"
        value >= gb -> "${transform(value, mb)} ${getString(R.string.mb)}"
        else -> "${transform(value, gb)} ${getString(R.string.gb)}"
    }
}

private fun transform(value: Long, size: Int) =
    BigDecimal(value).divide(BigDecimal(size), BigDecimal.ROUND_HALF_EVEN)
