package com.koleychik.core_files.extensions

import com.koleychik.core_files.AudioStorage
import com.koleychik.core_files.FilesStorage
import com.koleychik.core_files.ImagesStorage
import com.koleychik.core_files.VideoStorage

val imagesProjections = arrayOf(
    ImagesStorage._ID,
    ImagesStorage.DISPLAY_NAME,
    ImagesStorage.SIZE,
    ImagesStorage.DATE_ADDED,
    ImagesStorage.MIME_TYPE
)

val audioProjections = arrayOf(
    AudioStorage._ID,
    AudioStorage.DISPLAY_NAME,
    AudioStorage.ARTIST,
    AudioStorage.TITLE,
    AudioStorage.ALBUM,
    AudioStorage.DURATION,
    AudioStorage.SIZE,
    AudioStorage.DATE_ADDED,
    AudioStorage.MIME_TYPE
)

val videoProjections = arrayOf(
    VideoStorage._ID,
    VideoStorage.DISPLAY_NAME,
    VideoStorage.DURATION,
    VideoStorage.SIZE,
    VideoStorage.DATE_ADDED,
    VideoStorage.MIME_TYPE,
    VideoStorage.MIME_TYPE
)

val documentsProjections = arrayOf(
    FilesStorage._ID,
    FilesStorage.DISPLAY_NAME,
    FilesStorage.SIZE,
    FilesStorage.DATE_ADDED,
    FilesStorage.MIME_TYPE
)

val allFilesFromFolderProjections = arrayOf(
    FilesStorage._ID,
    FilesStorage.DISPLAY_NAME,
    FilesStorage.RELATIVE_PATH,
    FilesStorage.MIME_TYPE
)
