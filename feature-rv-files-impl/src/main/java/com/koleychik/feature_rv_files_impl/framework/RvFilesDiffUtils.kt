package com.koleychik.feature_rv_files_impl.framework

import androidx.recyclerview.widget.DiffUtil
import com.koleychik.models.fileCarcass.FileCarcass

class RvFilesDiffUtils(
    private val newList: List<FileCarcass>,
    private val oldList: List<FileCarcass>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition].id == oldList[oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]
}