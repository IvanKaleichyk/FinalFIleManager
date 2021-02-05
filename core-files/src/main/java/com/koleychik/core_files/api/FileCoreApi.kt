package com.koleychik.core_files.api

import com.koleychik.module_injector.component_holder.BaseApi

interface FileCoreApi: BaseApi {

    fun getFileRepository(): FilesRepository

}