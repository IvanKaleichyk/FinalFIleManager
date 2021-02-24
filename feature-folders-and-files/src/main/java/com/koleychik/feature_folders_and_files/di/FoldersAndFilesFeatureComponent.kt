package com.koleychik.feature_folders_and_files.di

import com.koleychik.feature_folders_and_files.ui.FoldersAndFilesFragment
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [FoldersAndFilesFeatureDependencies::class]
)
interface FoldersAndFilesFeatureComponent : FoldersAndFilesFeatureApi {

    fun inject(fragment: FoldersAndFilesFragment)

    companion object {
        fun initAndGet(dependencies: FoldersAndFilesFeatureDependencies) =
            DaggerFoldersAndFilesFeatureComponent
                .builder()
                .foldersAndFilesFeatureDependencies(dependencies)
                .build()
    }

}