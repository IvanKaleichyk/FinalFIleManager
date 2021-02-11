package com.koleychik.finalfilemanager.di.modules

import com.koleychik.feature_documents.di.DocumentFeatureDestroyer
import com.koleychik.feature_documents.di.DocumentsFeatureApi
import com.koleychik.feature_documents.di.DocumentsFeatureComponentHolder
import com.koleychik.feature_documents.di.DocumentsFeatureDependencies
import com.koleychik.feature_images.ImagesFeatureDestroyer
import com.koleychik.feature_images.di.ImagesFeatureComponentHolder
import com.koleychik.feature_images.di.ImagesFeatureDependencies
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationComponentHolder
import com.koleychik.feature_loading_api.FeatureLoadingApi
import com.koleychik.feature_loading_impl.di.LoadingComponentHolder
import com.koleychik.feature_music.di.MusicFeatureApi
import com.koleychik.feature_music.di.MusicFeatureComponentHolder
import com.koleychik.feature_music.di.MusicFeatureDependencies
import com.koleychik.feature_music.di.MusicFeatureDestroyer
import com.koleychik.feature_rv_common_api.api.RvMediaApi
import com.koleychik.feature_rv_common_impl.di.RvMediaComponentHolder
import com.koleychik.feature_rv_documents_api.RvFilesApi
import com.koleychik.feature_rv_files_impl.di.RvFilesAdapterComponentHolder
import com.koleychik.feature_rv_files_impl.di.RvFilesAdapterComponentHolder.init
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.feature_select_category.di.SelectCategoryComponentHolder
import com.koleychik.feature_select_category.di.SelectCategoryDependencies
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationComponentHolder
import com.koleychik.finalfilemanager.navigation.Navigator
import com.koleychik.module_injector.component_holder.BaseDependencies
import com.koleychik.module_injector.component_holder.BaseDestroyer
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideImagesFeatureApi(
        dependencies: ImagesFeatureDependencies,
        navigator: Navigator,
        destroyer: ImagesFeatureDestroyer
    ): ImagesFeatureApi {
        ImagesFeatureComponentHolder.init(dependencies, destroyer)
        ImagesFeatureNavigationComponentHolder.init(navigator)
        return ImagesFeatureComponentHolder.get()
    }

    @Provides
    fun provideMusicFeatureApi(
        dependencies: MusicFeatureDependencies,
        destroyer: MusicFeatureDestroyer
    ): MusicFeatureApi {
        MusicFeatureComponentHolder.init(dependencies, destroyer)
        return MusicFeatureComponentHolder.get()
    }

    @Provides
    fun provideDocumentsFeatureApi(
        dependencies: DocumentsFeatureDependencies,
        destroyer: DocumentFeatureDestroyer
    ): DocumentsFeatureApi {
        DocumentsFeatureComponentHolder.init(dependencies, destroyer)
        return DocumentsFeatureComponentHolder.get()
    }

    @Provides
    fun provideSelectCategoryApi(
        dependencies: SelectCategoryDependencies,
        navigator: Navigator,
        destroyer: BaseDestroyer
    ): SelectCategoryApi {
        SelectCategoryComponentHolder.init(dependencies, destroyer)
        SelectCategoryNavigationComponentHolder.init(navigator)
        return SelectCategoryComponentHolder.get()
    }

    @Provides
    fun provideRvMediaApi(destroyer: BaseDestroyer): RvMediaApi {
        RvMediaComponentHolder.init(object : BaseDependencies {}, destroyer)
        return RvMediaComponentHolder.get()
    }

    @Provides
    fun provideRvFilesApi(destroyer: BaseDestroyer): RvFilesApi {
        init(object : BaseDependencies {}, destroyer)
        return RvFilesAdapterComponentHolder.get()
    }

    @Provides
    fun provideLoadingApi(destroyer: BaseDestroyer): FeatureLoadingApi {
        LoadingComponentHolder.init(object : BaseDependencies {}, destroyer)
        return LoadingComponentHolder.get()
    }

}