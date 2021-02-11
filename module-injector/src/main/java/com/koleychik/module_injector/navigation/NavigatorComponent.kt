package com.koleychik.module_injector.navigation

interface NavigatorComponentHolder<Api : NavigatorApi> {

    fun init(api: Api)
    fun get(): Api
    fun reset()
}