package com.koleychik.feature_rv_common_api.api

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.models.fileCarcass.media.MediaCarcass

abstract class RvMediaViewHolderApi(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: MediaCarcass, position: Int)
    abstract fun clear()
}