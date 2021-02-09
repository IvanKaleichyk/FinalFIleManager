package com.koleychik.finalfilemanager.di.modules

import com.koleychik.feature_images.di.ImagesFeatureComponentHolder
import com.koleychik.feature_images.di.ImagesFeatureDependencies
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_images.navigation.ImagesFeatureNavigationComponentHolder
import com.koleychik.feature_loading_api.FeatureLoadingApi
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_loading_impl.di.LoadingComponentHolder
import com.koleychik.feature_rv_common_api.RvMediaApi
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
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideImagesFeatureApi(
        dependencies: ImagesFeatureDependencies,
        navigator: Navigator
    ): ImagesFeatureApi {
        ImagesFeatureComponentHolder.init(dependencies)
        ImagesFeatureNavigationComponentHolder.init(navigator)
        return ImagesFeatureComponentHolder.get()
    }

    @Provides
    fun provideSelectCategoryApi(
        dependencies: SelectCategoryDependencies,
        navigator: Navigator
    ): SelectCategoryApi {
        SelectCategoryComponentHolder.init(dependencies)
        SelectCategoryNavigationComponentHolder.init(navigator)
        return SelectCategoryComponentHolder.get()
    }

    @Provides
    fun provideRvMediaApi(): RvMediaApi{
        return RvMediaComponentHolder.get().apply {
            init(object : BaseDependencies{})
        }
    }

    @Provides
    fun provideRvFilesApi(): RvFilesApi {
        return RvFilesAdapterComponentHolder.get().apply {
            init(object : BaseDependencies {})
        }
    }

    @Provides
    fun provideLoadingApi(): FeatureLoadingApi{
       return LoadingComponentHolder.get().apply {
            init(object : BaseDependencies{})
        }
    }

}