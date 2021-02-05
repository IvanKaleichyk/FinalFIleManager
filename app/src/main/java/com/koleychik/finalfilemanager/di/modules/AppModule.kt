package com.koleychik.finalfilemanager.di.modules

import android.content.Context
import com.koleychik.feature_images.di.api.ImagesFeatureApi
import com.koleychik.feature_select_category.SelectCategoryApi
import com.koleychik.finalfilemanager.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext() = context

    @Provides
    @Singleton
    fun provideNavigator(
        imagesFeatureApi: Provider<ImagesFeatureApi>,
        selectCategoryApi: Provider<SelectCategoryApi>
    ) = Navigator(imagesFeatureApi, selectCategoryApi)

}