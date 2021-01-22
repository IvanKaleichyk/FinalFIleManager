package com.koleychik.feature_rv_documents_api

import androidx.recyclerview.widget.RecyclerView
import com.koleychik.models.fileCarcass.FileCarcass


abstract class FeatureRvFilesAdapterApi<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    abstract fun submitList(newList: List<FileCarcass>)

}