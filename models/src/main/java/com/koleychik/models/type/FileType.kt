package com.koleychik.models.type

import com.koleychik.models.R

sealed class FileType(val id: Int, val name: Int, val imgRes: Int) {
    object ImageType : FileType(0, R.string.image, R.drawable.image_icon)
    object AudioType : FileType(1, R.string.audio, R.drawable.audio_icon)
    object VideoType : FileType(2, R.string.video, R.drawable.video_icon)
    object TextType : FileType(3, R.string.text, R.drawable.text_icon)
    object FolderType : FileType(4, R.string.folder, R.drawable.folder_icon)
    object OtherType : FileType(5, R.string.other, R.drawable.file_icon)
}
