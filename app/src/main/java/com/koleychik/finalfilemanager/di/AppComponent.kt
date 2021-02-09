package com.koleychik.finalfilemanager.di

import com.koleychik.finalfilemanager.App
import com.koleychik.finalfilemanager.MainActivity
import com.koleychik.finalfilemanager.di.modules.ApiModule
import com.koleychik.finalfilemanager.di.modules.AppModule
import com.koleychik.finalfilemanager.di.modules.DependenciesModule
import com.koleychik.finalfilemanager.di.modules.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NavigationModule::class, DependenciesModule::class, ApiModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(app: App)

}