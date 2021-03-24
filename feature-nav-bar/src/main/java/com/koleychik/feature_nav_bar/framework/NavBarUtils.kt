package com.koleychik.feature_nav_bar.framework

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

interface NavBarUtils {

    fun setup(
        bottomNavigationView: BottomNavigationView,
        fragmentManager: FragmentManager,
        containerId: Int
    )
//    fun fileNavHostFragment(): NavHostFragment
//    fun folderNavHostFragment(): NavHostFragment
//    fun getSparseArray(): SparseArray<NavHostFragment>
}