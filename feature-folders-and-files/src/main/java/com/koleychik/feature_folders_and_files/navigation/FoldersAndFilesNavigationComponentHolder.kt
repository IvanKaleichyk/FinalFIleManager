package com.koleychik.feature_folders_and_files.navigation

import com.koleychik.module_injector.navigation.NavigatorComponentHolder

object FoldersAndFilesNavigationComponentHolder :
    NavigatorComponentHolder<FoldersAndFilesNavigationApi> {

    var navigationApi: FoldersAndFilesNavigationApi? = null

    override fun init(api: FoldersAndFilesNavigationApi) {
        navigationApi = api
    }

    override fun get(): FoldersAndFilesNavigationApi = navigationApi!!

    override fun reset() {
        navigationApi = null
    }
}