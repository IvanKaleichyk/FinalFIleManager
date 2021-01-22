package com.koleychik.feature_rv_documents_api

import androidx.recyclerview.widget.RecyclerView

interface FeatureRvFilesApi {
    fun <T : RecyclerView.ViewHolder> getAdapter(): FeatureRvFilesAdapterApi<T>
}