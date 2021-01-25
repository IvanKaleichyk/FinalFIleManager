package com.koleychik.feature_rv_common_api

import androidx.recyclerview.widget.RecyclerView


interface FeatureRvMediaApi {
    fun getViewHolder(): FeatureRvMediaViewHolderApi
    fun adapter(): FeatureRvMediaAdapterApi
}