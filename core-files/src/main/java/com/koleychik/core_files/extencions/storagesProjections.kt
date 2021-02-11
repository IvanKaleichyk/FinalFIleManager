package com.koleychik.core_files.extencions

import com.koleychik.core_files.AudioStorage
import com.koleychik.core_files.FilesStorage
import com.koleychik.core_files.ImagesStorage
import com.koleychik.core_files.VideoStorage

val imagesProjections = arrayOf(
    ImagesStorage._ID,
    ImagesStorage.DISPLAY_NAME,
    ImagesStorage.SIZE,
    ImagesStorage.DATE_ADDED
)

val audioProjections = arrayOf(
    AudioStorage._ID,
    AudioStorage.DISPLAY_NAME,
    AudioStorage.AUTHOR,
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

fun getSizeAbbreviation(size: Long): String {
    return size.toString()
}