package com.koleychik.feature_rv_files_impl.di

import com.koleychik.feature_rv_documents_api.RvFilesApi
import dagger.Component

@Component(modules = [RvFilesAdapterModule::class])
interface RvFilesAdapterComponent : RvFilesApi {

    companion object {
        fun initAndGet(): RvFilesAdapterComponent = DaggerRvFilesAdapterComponent.builder().build()
    }

}