package com.koleychik.models

enum class CategoryModel(val id: Int, val nameRes: Int, val imgRes: Int){
    IMAGE(0, R.string.images, R.drawable.images_icon_48_black),
    VIDEO(1, R.string.video, R.drawable.video_icon_48_black),
    MUSIC(2, R.string.music, R.drawable.music_icon_48_black),
    DOCUMENT(3, R.string.documents, R.drawable.documents_icon_48_black)
}
