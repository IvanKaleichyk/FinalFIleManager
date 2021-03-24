package com.koleychik.finalfilemanager.utils

import android.util.SparseIntArray
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.koleychik.finalfilemanager.R

fun BottomNavigationView.setup(
    fragmentManager: FragmentManager,
    mapItemIdToNavGraph: SparseIntArray,
    containerId: Int
) {
    lateinit var controller: NavController
    var currentItemId = selectedItemId
    var firstFragmentItemId = 0
    var isOnFirstFragment: Boolean
    var index = 0
    mapItemIdToNavGraph.forEach { itemId, navGraphId ->
        if (index == 0) firstFragmentItemId = itemId
        val fragment =
            fragmentManager.obtainFragment(navGraphId, getFragmentTag(itemId), containerId)
        if (selectedItemId == itemId) {
            controller = (fragment as NavHostFragment).navController
            fragmentManager.attachFirstFragment(fragment, index == 0)
        } else fragmentManager.detachFragment(fragment)
        index++
    }

    isOnFirstFragment = firstFragmentItemId == selectedItemId

    setOnNavigationItemSelectedListener { item ->
        if (fragmentManager.isStateSaved) false
        else {
            if (currentItemId != item.itemId) {
                currentItemId = item.itemId

                fragmentManager.popBackStack(
                    getFragmentTag(firstFragmentItemId),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )

                val currentFragment =
                    fragmentManager.obtainFragment(
                        mapItemIdToNavGraph[currentItemId],
                        getFragmentTag(currentItemId),
                        containerId
                    )

                if (firstFragmentItemId != currentItemId)
                    fragmentManager.attachFragment(
                        currentFragment,
                        currentItemId,
                        getFragmentTag(firstFragmentItemId),
                        mapItemIdToNavGraph
                    )

                isOnFirstFragment = currentItemId == firstFragmentItemId
                controller = (currentFragment as NavHostFragment).navController
                true
            } else false
        }
    }

    setupItemReselected(fragmentManager)

    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(getFragmentTag(firstFragmentItemId))) {
            this.selectedItemId = firstFragmentItemId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).

        if (controller.currentDestination == null) {
            controller.navigate(controller.graph.id)
        }

    }
}

private fun BottomNavigationView.setupItemReselected(
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val selectedFragment = fragmentManager.findFragmentByTag(getFragmentTag(item.itemId))
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun FragmentManager.obtainFragment(
    navGraphId: Int,
    fragmentTag: String,
    containerId: Int
): Fragment {
//  Check if fragment exists
    findFragmentByTag(fragmentTag)?.let { return it }

    return NavHostFragment.create(navGraphId).apply {
        addFragment(this, fragmentTag, containerId)
    }
}

private fun FragmentManager.attachFragment(
    fragment: Fragment,
    currentItemId: Int,
    firstFragmentTag: String,
    mapItemIdToNavGraph: SparseIntArray
) {
    beginTransaction()
        .setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )
        .attach(fragment)
        .setPrimaryNavigationFragment(fragment)
        .apply {
            // Detach all other Fragments
            mapItemIdToNavGraph.forEach { itemId, _ ->
                if (itemId != currentItemId) {
                    detach(findFragmentByTag(firstFragmentTag)!!)
                }
            }
        }
        .addToBackStack(firstFragmentTag)
        .setReorderingAllowed(true)
        .commit()
}

private fun FragmentManager.attachFirstFragment(fragment: Fragment, isPrimaryFragment: Boolean) {
    beginTransaction().attach(fragment)
        .apply { if (isPrimaryFragment) setPrimaryNavigationFragment(fragment) }.commitNow()
}

private fun FragmentManager.detachFragment(fragment: Fragment) {
    beginTransaction().detach(fragment).commitNow()
}

private fun FragmentManager.addFragment(fragment: Fragment, fragmentTag: String, containerId: Int) {
    beginTransaction().add(containerId, fragment, fragmentTag).commitNow()
}

private fun getFragmentTag(itemId: Int) = "FragmentTag$itemId"