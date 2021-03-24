package com.koleychik.finalfilemanager

import android.app.Application
import com.koleychik.finalfilemanager.di.AppComponent
import com.koleychik.finalfilemanager.di.DaggerAppComponent
import com.koleychik.finalfilemanager.di.modules.AppModule

class App: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
    }
}