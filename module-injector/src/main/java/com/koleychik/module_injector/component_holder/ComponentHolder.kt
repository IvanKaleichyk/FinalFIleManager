package com.koleychik.module_injector.component_holder

interface ComponentHolder<A : BaseApi, D : BaseDependencies> {
    fun init(dependency: D)
    fun get(): A
    fun reset()
}