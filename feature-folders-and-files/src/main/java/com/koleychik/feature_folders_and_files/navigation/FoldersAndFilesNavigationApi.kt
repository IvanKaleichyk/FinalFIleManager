package com.koleychik.feature_folders_and_files.navigation

import android.os.Bundle
import com.koleychik.module_injector.navigation.NavigatorApi

interface FoldersAndFilesNavigationApi : NavigatorApi {

    fun openFileInNewFragment(bundle: Bundle)

}