package com.koleychik.feature_folders_and_files.di

import com.koleychik.module_injector.component_holder.BaseDestroyer

interface FoldersAndFilesFeatureDestroyer : BaseDestroyer {

    fun destroy()

}