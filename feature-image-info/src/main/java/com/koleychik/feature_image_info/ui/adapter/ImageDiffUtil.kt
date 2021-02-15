package com.koleychik.feature_image_info.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.fileCarcass.media.ImageModel

internal class ImageDiffUtil(
    private val newList: List<ImageModel>,
    private val oldList: List<ImageModel>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].id == oldList[oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]


}