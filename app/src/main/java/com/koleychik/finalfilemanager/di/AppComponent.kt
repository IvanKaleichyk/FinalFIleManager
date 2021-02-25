package com.koleychik.finalfilemanager.di

import com.koleychik.finalfilemanager.App
import com.koleychik.finalfilemanager.MainActivity
import com.koleychik.finalfilemanager.di.modules.ApiModule
import com.koleychik.finalfilemanager.di.modules.AppModule
import com.koleychik.finalfilemanager.di.modules.DependenciesModule
import com.koleychik.finalfilemanager.di.modules.DestroyerModule
import com.koleychik.finalfilemanager.test.TestLoadingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DependenciesModule::class,
        ApiModule::class,
        DestroyerModule::class]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(app: App)
    fun inject(activity: TestLoadingFragment)
}