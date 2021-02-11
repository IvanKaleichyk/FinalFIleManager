package com.koleychik.finalfilemanager.di

import com.koleychik.finalfilemanager.App
import com.koleychik.finalfilemanager.MainActivity
import com.koleychik.finalfilemanager.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        DependenciesModule::class,
        ApiModule::class,
        DestroyerModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(app: App)
}