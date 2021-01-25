package com.koleychik.feature_rv_common_api

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.media.MediaCarcass

abstract class FeatureRvMediaAdapterApi : RecyclerView.Adapter<FeatureRvMediaViewHolderApi>() {
    abstract fun submitList(newList: List<MediaCarcass>)
}