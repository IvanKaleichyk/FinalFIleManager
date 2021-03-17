package com.koleychik.module_injector.navigation

import androidx.fragment.app.Fragment

object NavigationSystem {

    var onStartFeature: ((fragment: Fragment) -> Unit)? = null

//    fun setOnFeatureStart(onStartFeature: (fragment: Fragment) -> Unit) {
//        this.onStartFeature = onStartFeature
//    }
//
//    fun clearOnDestinationChange() {
//        onStartFeature = null
//    }

}