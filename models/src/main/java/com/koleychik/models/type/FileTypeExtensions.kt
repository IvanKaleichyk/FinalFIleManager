package com.koleychik.models.type

fun getFileType(type: String, isFolder: Boolean = false): FileType {
    return when {
        isFolder -> FileType.FolderType
        type.startsWith("image") -> FileType.ImageType
        type.startsWith("video") -> FileType.VideoType
        type.startsWith("audio") -> FileType.AudioType
        type.startsWith("text") -> FileType.TextType
        else -> FileType.OtherType
    }
}