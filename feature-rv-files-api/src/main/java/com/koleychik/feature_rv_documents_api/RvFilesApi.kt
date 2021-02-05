package com.koleychik.feature_rv_documents_api

import com.koleychik.module_injector.component_holder.BaseApi

interface RvFilesApi : BaseApi {
    fun getAdapter(): RvFilesAdapterApi
    fun getViewHolder(): RvFilesAdapterViewHolder
}