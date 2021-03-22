package com.koleychik.feature_nav_bar.framework

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.koleychik.feature_nav_bar.R

fun BottomNavigationView.setup(
    fragmentManager: FragmentManager,
    containerId: Int,
    sparseArray: SparseArray<NavHostFragment>
) {
    var currentFragmentItemId = 0
    var isOnTheFirstFragment: Boolean
    var firstFragmentItemId = 0
    var index = 0
    sparseArray.forEach { itemId, _ ->
        if (index == 0) firstFragmentItemId = itemId
        val newFragment = fragmentManager.obtainFragment(itemId, sparseArray, containerId)
        if (selectedItemId == itemId) {
            currentFragmentItemId = itemId
            fragmentManager.attachFragment(newFragment, index == 0)
        } else fragmentManager.detachFragment(newFragment)
        index++
    }

    isOnTheFirstFragment = firstFragmentItemId == currentFragmentItemId

    setOnNavigationItemSelectedListener { menuItem ->
        if (fragmentManager.isStateSaved) {
            false
        } else {
            if (currentFragmentItemId == menuItem.itemId) false
            else {
                currentFragmentItemId = menuItem.itemId
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(firstFragmentItemId)
                val currentFragment =
                    fragmentManager.findFragmentByTag(getFragmentTag(currentFragmentItemId))!!
                if (firstFragmentItemId != currentFragmentItemId) {

                    fragmentManager.selectNewFragment(
                        currentFragment,
                        currentFragmentItemId,
                        firstFragmentItemId,
                        sparseArray
                    )
                }
                isOnTheFirstFragment = firstFragmentItemId == currentFragmentItemId
                true
            }
        }
    }
}

fun FragmentManager.selectNewFragment(
    currentFragment: Fragment,
    currentItemId: Int,
    firstFragmentItemId: Int,
    sparseArray: SparseArray<NavHostFragment>
) {
    beginTransaction()
        .setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )
        .attach(currentFragment)
        .setPrimaryNavigationFragment(currentFragment)
        .apply {
            // Detach all other Fragments
            sparseArray.forEach { itemId, fragment ->
                if (itemId != currentItemId) {
                    detach(fragment)
                }
            }
        }
        .addToBackStack(getFragmentTag(firstFragmentItemId))
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentManager.popBackStack(firstFragmentItemId: Int) {
    popBackStack(
        getFragmentTag(firstFragmentItemId),
        FragmentManager.POP_BACK_STACK_INCLUSIVE
    )
}

fun FragmentManager.obtainFragment(
    itemId: Int,
    sparseArray: SparseArray<NavHostFragment>,
    containerId: Int
): NavHostFragment {
    val tag = getFragmentTag(itemId)
    val existFragment = findFragmentByTag(tag) as NavHostFragment?
    existFragment?.let { return it }

    val fragment = sparseArray[itemId]!!
    addFragment(fragment, containerId, tag)
    return fragment
}

fun FragmentManager.attachFragment(fragment: Fragment, isPrimaryNavFragment: Boolean) {
    beginTransaction()
        .attach(fragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(fragment)
            }
        }
        .commitNow()
}

fun FragmentManager.detachFragment(fragment: Fragment) {
    beginTransaction().detach(fragment).commitNow()
}

fun FragmentManager.addFragment(fragment: Fragment, containerId: Int, fragmentTag: String) {
    beginTransaction().add(containerId, fragment, fragmentTag).commitNow()
}
