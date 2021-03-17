package com.koleychik.feature_nav_bar

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.koleychik.feature_nav_bar.framework.NavHosts

const val TAG = "MAIN_APP_TAG"

fun BottomNavigationView.setup(fragmentManager: FragmentManager, navHosts: NavHosts) {

    var activeFragment =
        fragmentManager.findFragmentByTag(getFragmentTag(selectedItemId)) ?: getFragmentByItemId(
            fragmentManager,
            navHosts,
            this.selectedItemId
        )

    setOnNavigationItemSelectedListener {


        Log.d(TAG, "setOnNavigationItemSelectedListener")

        val newFragment = fragmentManager.findFragmentByTag(getFragmentTag(it.itemId))
            ?: getFragmentByItemId(fragmentManager, navHosts, it.itemId)

        Log.d(TAG, "newFragment = $newFragment")

        if (newFragment != activeFragment) {
            fragmentManager.detachFragment(activeFragment)

            fragmentManager.attachFragment(newFragment)
            activeFragment = newFragment
        }
        true
    }
}


fun FragmentManager.detachFragment(fragment: Fragment) {
    beginTransaction().detach(fragment).commit()
}

fun FragmentManager.attachFragment(newFragment: Fragment) {
    beginTransaction()
        .setPrimaryNavigationFragment(newFragment)
        .attach(newFragment)
        .commit()
}

fun FragmentManager.addFragment(fragment: Fragment, index: Int) {

    beginTransaction().add(R.id.bottom_navController, fragment, getFragmentTag(index)).commit()
}

private fun getFragmentByItemId(
    fragmentManager: FragmentManager,
    navHosts: NavHosts,
    id: Int
): Fragment {
    val fragment = when (id) {
        R.id.files -> NavHostFragment.create(navHosts.fileNavHost())
        R.id.folders -> NavHostFragment.create(navHosts.folderNavHost())
        else -> NavHostFragment.create(navHosts.fileNavHost())
    }
    fragmentManager.addFragment(fragment, id)
    return fragment
}

private fun getFragmentTag(id: Int) = "fragment$id"
