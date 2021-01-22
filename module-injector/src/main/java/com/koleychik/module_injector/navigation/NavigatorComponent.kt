package com.koleychik.module_injector.navigation

interface NavigatorComponent<Api : NavigatorApi> {

    fun init(api: Api)
    fun get(): Api
    fun reset()

}