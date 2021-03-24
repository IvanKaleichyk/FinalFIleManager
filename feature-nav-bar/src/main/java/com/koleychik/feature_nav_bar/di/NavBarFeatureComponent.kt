package com.koleychik.feature_nav_bar.di

import com.koleychik.feature_nav_bar.framework.NavBarFragment
import dagger.Component

@Component(dependencies = [NavBarFeatureDependencies::class])
interface NavBarFeatureComponent : NavBarFeatureApi {

    fun inject(fragment: NavBarFragment)

    companion object {
        fun initAndGet(dependencies: NavBarFeatureDependencies) =
            DaggerNavBarFeatureComponent.builder().navBarFeatureDependencies(dependencies).build()
    }

}