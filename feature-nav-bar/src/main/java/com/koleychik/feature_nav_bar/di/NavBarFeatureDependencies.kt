package com.koleychik.feature_nav_bar.di

import com.koleychik.feature_nav_bar.framework.NavHosts
import com.koleychik.module_injector.component_holder.BaseDependencies

interface NavBarFeatureDependencies : BaseDependencies {

    fun navHosts(): NavHosts

}