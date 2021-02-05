package com.koleychik.feature_music.adapters

import android.support.v4.media.MediaMetadataCompat
import androidx.recyclerview.widget.DiffUtil

class MusicDiffUtil(
    private val newList: List<MediaMetadataCompat>,
    private val oldList: List<MediaMetadataCompat>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].description.mediaId == newList[newItemPosition].description.mediaId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}