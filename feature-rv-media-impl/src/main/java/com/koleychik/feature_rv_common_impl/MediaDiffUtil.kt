package com.koleychik.feature_rv_common_impl

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.fileCarcass.media.MediaCarcass

class MediaDiffUtil(
    private val newList: List<MediaCarcass>,
    private val oldList: List<MediaCarcass>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].id == oldList[oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]


}