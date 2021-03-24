package com.koleychik.feature_folders_and_files.navigation

import android.os.Bundle
import androidx.navigation.NavController

interface FoldersAndFilesNavigationApi {

    fun openFileInNewFragment(controller: NavController, bundle: Bundle)

}