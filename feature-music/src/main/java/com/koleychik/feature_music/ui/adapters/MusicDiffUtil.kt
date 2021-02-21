package com.koleychik.feature_music.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.fileCarcass.MusicModel

class MusicDiffUtil(
    private val newList: List<MusicModel>,
    private val oldList: List<MusicModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id== newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}