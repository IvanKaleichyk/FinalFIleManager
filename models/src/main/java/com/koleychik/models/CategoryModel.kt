package com.koleychik.models

sealed class CategoryModel(val id: Int, val name: Int, val imgRes: Int) {
    object ImageCategory : CategoryModel(0, R.string.images, R.drawable.images_icon_48_black)
    object VideoCategory : CategoryModel(1, R.string.video, R.drawable.video_icon_48_black)
    object MusicCategory : CategoryModel(2, R.string.music, R.drawable.music_icon_48_black)
    object DownloadsCategory :
        CategoryModel(3, R.string.documents, R.drawable.documents_icon_48_black)
}
