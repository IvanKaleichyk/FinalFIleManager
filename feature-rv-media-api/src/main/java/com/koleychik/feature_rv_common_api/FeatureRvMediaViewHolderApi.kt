package com.koleychik.feature_rv_common_api

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.koleychik.models.fileCarcass.media.MediaCarcass

abstract class FeatureRvMediaViewHolderApi(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: MediaCarcass)
    abstract fun clear()
}