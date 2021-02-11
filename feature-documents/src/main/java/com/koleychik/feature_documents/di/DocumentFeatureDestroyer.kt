package com.koleychik.feature_documents.di

import com.koleychik.module_injector.component_holder.BaseDestroyer

interface DocumentFeatureDestroyer : BaseDestroyer {

    fun destroy()
}