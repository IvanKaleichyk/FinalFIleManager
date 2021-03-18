package com.koleychik.injector

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