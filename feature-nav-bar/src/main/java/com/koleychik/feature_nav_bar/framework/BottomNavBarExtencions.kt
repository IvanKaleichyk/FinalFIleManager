package com.koleychik.feature_nav_bar.framework

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.koleychik.feature_nav_bar.R
import com.koleychik.injector.AppConstants.TAG


fun BottomNavigationView.setup(
    fragmentManager: FragmentManager,
    navBarUtils: NavBarUtils
) {
    val mapFragment = fragmentManager.createMap(navBarUtils)
    var currentFragment =
        fragmentManager.findFragmentByTag(getFragmentTag(selectedItemId))
            ?: fragmentManager.getFragmentByItemId(selectedItemId, mapFragment, navBarUtils)


    setOnNavigationItemSelectedListener {
        val newFragment: Fragment = fragmentManager.findFragmentByTag(getFragmentTag(it.itemId))
            ?: fragmentManager.getFragmentByItemId(it.itemId, mapFragment, navBarUtils)

        if (newFragment != currentFragment) {
            Log.d(TAG, "newFragment != currentFragment")
            fragmentManager.detachFragment(currentFragment)

            fragmentManager.attachFragment(newFragment)
            currentFragment = newFragment
        } else Log.d(TAG, "newFragment != currentFragment is False")

        true
    }
}

fun FragmentManager.createMap(navBarUtils: NavBarUtils): Map<Int, Fragment> {
    val fileFragment = navBarUtils.fileNavHostFragment()
    val folderFragment = navBarUtils.folderNavHostFragment()
    addFragment(fileFragment, R.id.files)
    addFragment(folderFragment, R.id.folders)
    attachFragment(fileFragment)
    detachFragment(folderFragment)
    return mapOf(R.id.files to fileFragment, R.id.folders to folderFragment)
}

fun FragmentManager.attachFragment(newFragment: Fragment) {
    beginTransaction()
        .setPrimaryNavigationFragment(newFragment)
        .attach(newFragment)
        .commit()
}

fun FragmentManager.addFragment(fragment: Fragment, id: Int) {
    beginTransaction().add(R.id.bottom_navController, fragment, getFragmentTag(id)).commit()
}

fun FragmentManager.detachFragment(fragment: Fragment) {
    Log.d(TAG, "detach Fragment")
    beginTransaction().detach(fragment).commit()
}

private fun FragmentManager.getFragmentByItemId(
    id: Int,
    map: Map<Int, Fragment>,
    navBarUtils: NavBarUtils
): Fragment {
    val fragment = map[id] ?: navBarUtils.fileNavHostFragment()
//    addFragment(fragment, id)
    return fragment
}

fun getFragmentTag(index: Int): String = "fragmentTag$index"