package com.koleychik.finalfilemanager.di.modules

import android.content.Context
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.core_files.di.FileCoreComponent
import com.koleychik.feature_documents.di.DocumentsFeatureDependencies
import com.koleychik.feature_folders_and_files.di.FoldersAndFilesFeatureDependencies
import com.koleychik.feature_folders_and_files.navigation.FoldersAndFilesNavigationApi
import com.koleychik.feature_image_info.di.ImageInfoFeatureDependencies
import com.koleychik.feature_images.di.ImagesFeatureDependencies
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationApi
import com.koleychik.feature_loading_api.FeatureLoadingApi
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_music.di.MusicFeatureDependencies
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_rv_common_api.api.RvMediaApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_rv_documents_api.RvFilesApi
import com.koleychik.feature_searching_impl.di.SearchingFeatureComponent
import com.koleychik.feature_searching_impl.framework.SearchingUIApi
import com.koleychik.feature_select_category.SelectCategoryNavigationApi
import com.koleychik.feature_select_category.di.SelectCategoryDependencies
import com.koleychik.feature_video.di.VideoFeatureDependencies
import com.koleychik.finalfilemanager.navigation.Navigator
import com.koleychik.module_injector.component_holder.BaseDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependenciesModule {

    @Provides
    @Singleton
    fun provideFoldersAndFilesFeatureDependencies(
        context: Context,
        featureLoadingApi: FeatureLoadingApi,
        navigator: Navigator,
        rvFilesApi: RvFilesApi
    ) = object : FoldersAndFilesFeatureDependencies {
        override fun repository(): FilesRepository =
            FileCoreComponent.get(context).getFileRepository()

        override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()
        override fun searchingUIApi(): SearchingUIApi =
            SearchingFeatureComponent.get().searchingUIApi()

        override fun navigationApi(): FoldersAndFilesNavigationApi = navigator
        override fun adapterApi(): RvFilesAdapterApi = rvFilesApi.getAdapter()
    }

    @Provides
    @Singleton
    fun provideMusicFeatureDependencies(context: Context, featureLoadingApi: FeatureLoadingApi) =
        object : MusicFeatureDependencies {
            override fun filesRepository(): FilesRepository =
                FileCoreComponent.get(context).getFileRepository()

            override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()
            override fun searchingUIApi(): SearchingUIApi =
                SearchingFeatureComponent.get().searchingUIApi()
        }

    @Provides
    @Singleton
    fun provideDocumentsDependencies(
        context: Context,
        featureLoadingApi: FeatureLoadingApi,
        filesApi: RvFilesApi
    ) = object : DocumentsFeatureDependencies {
        override fun repository(): FilesRepository =
            FileCoreComponent.get(context).getFileRepository()

        override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()
        override fun rvFilesAdapterApi(): RvFilesAdapterApi = filesApi.getAdapter()
        override fun searchingUIApi(): SearchingUIApi =
            SearchingFeatureComponent.get().searchingUIApi()
    }

    @Provides
    @Singleton
    fun provideVideoFeatureDependencies(
        context: Context,
        featureLoadingApi: FeatureLoadingApi,
        rvMediaApi: RvMediaApi
    ) = object : VideoFeatureDependencies {
        override fun repository(): FilesRepository =
            FileCoreComponent.get(context).getFileRepository()

        override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()

        override fun mediaAdapter(): RvMediaAdapterApi = rvMediaApi.rvMediaAdapterApi()
        override fun searchingUiApi(): SearchingUIApi =
            SearchingFeatureComponent.get().searchingUIApi()

    }

    @Provides
    @Singleton
    fun provideImageInfoFeatureDependencies(context: Context) =
        object : ImageInfoFeatureDependencies {
            override fun getRepository(): FilesRepository =
                FileCoreComponent.get(context).getFileRepository()
        }

    @Provides
    @Singleton
    fun provideImagesFeatureDependencies(
        context: Context,
        navigator: Navigator,
        rvMediaApi: RvMediaApi,
        featureLoadingApi: FeatureLoadingApi,
    ) = object : ImagesFeatureDependencies {
        override fun repository(): FilesRepository =
            FileCoreComponent.get(context).getFileRepository()

        override fun navigator(): ImagesFeatureNavigationApi = navigator
        override fun rvMediaAdapterApi(): RvMediaAdapterApi = rvMediaApi.rvMediaAdapterApi()
        override fun loadingApi(): LoadingApi = featureLoadingApi.getLoadingApi()
        override fun searchingUIApi() = SearchingFeatureComponent.get().searchingUIApi()
    }

    @Provides
    @Singleton
    fun provideSelectCategoryDependencies(navigator: Navigator) =
        object : SelectCategoryDependencies {
            override fun selectCategoryNavigationApi(): SelectCategoryNavigationApi = navigator
        }

    @Provides
    @Singleton
    fun provideBaseDependencies() = object : BaseDependencies {}

}