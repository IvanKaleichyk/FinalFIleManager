package com.koleychik.feature_rv_common_api

import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_rv_common_api.api.RvMediaViewHolderApi
import com.koleychik.models.fileCarcass.media.MediaCarcass

abstract class RvMediaAdapterApi : RecyclerView.Adapter<RvMediaViewHolderApi>() {
    abstract fun submitList(newList: List<MediaCarcass>)
    abstract fun setOnCLick(onCLick: ((model: MediaCarcass, position: Int) -> Unit)?)
}