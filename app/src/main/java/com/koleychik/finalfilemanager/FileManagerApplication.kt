package com.koleychik.finalfilemanager

import android.app.Application
import com.koleychik.finalfilemanager.di.AppComponent
import com.koleychik.finalfilemanager.di.modules.AppModule

class FileManagerApplication: Application() {

    companion object{
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
        component.inject(this)
    }
}