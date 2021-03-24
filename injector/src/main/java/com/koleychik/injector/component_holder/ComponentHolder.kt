package com.koleychik.module_injector.component_holder

interface ComponentHolder<A : BaseApi, D : BaseDependencies, Destroyer : BaseDestroyer> {
    fun init(dependencies: D, destroyer: Destroyer)
    fun get(): A
    fun reset()
}