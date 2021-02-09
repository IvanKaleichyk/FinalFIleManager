package com.koleychik.feature_rv_common_impl.di

import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_rv_common_api.RvMediaViewHolderApi
import com.koleychik.feature_rv_common_impl.framework.RvMediaAdapterImpl
import dagger.Binds
import dagger.Module

@Module
internal abstract class RvMediaModule {

    @Binds
    abstract fun provideRvMediaAdapterApi(api: RvMediaAdapterImpl): RvMediaAdapterApi
}