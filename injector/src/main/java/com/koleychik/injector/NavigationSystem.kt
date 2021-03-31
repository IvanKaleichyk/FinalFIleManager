package com.koleychik.injector

import androidx.fragment.app.Fragment

object NavigationSystem {

    var onStartFeature: ((fragment: Fragment) -> Unit)? = null

}