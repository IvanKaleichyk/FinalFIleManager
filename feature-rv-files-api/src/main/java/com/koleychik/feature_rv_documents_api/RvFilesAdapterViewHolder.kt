package com.koleychik.feature_rv_documents_api

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.models.fileCarcass.FileCarcass

abstract class RvFilesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(model: FileCarcass)

}