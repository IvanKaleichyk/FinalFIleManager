package com.koleychik.feature_image_info.di

import com.koleychik.core_files.api.FilesRepository
import com.koleychik.module_injector.component_holder.BaseDependencies

interface ImageInfoFeatureDependencies : BaseDependencies {
    fun getRepository(): FilesRepository
}