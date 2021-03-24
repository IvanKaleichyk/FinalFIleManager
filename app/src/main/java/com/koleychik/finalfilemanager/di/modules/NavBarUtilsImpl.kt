package com.koleychik.finalfilemanager.di.modules

import android.util.SparseArray
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.koleychik.finalfilemanager.R

fun BottomNavigationView.setup(
    fragmentManager: FragmentManager,
    listNavHostsIds: List<Int>,
    containerId: Int
) {
    var firstFragmentGraphId = 0

    val selectedNavController = MutableLiveData<NavController>()

    val mapGraphIdToTag = SparseArray<String>()
    listNavHostsIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)
        val fragment = fragmentManager.obtainNavHostFragment(fragmentTag, navGraphId, containerId)

        val graphId = fragment.navController.graph.id
        if (index == 0) firstFragmentGraphId = graphId
        mapGraphIdToTag[graphId] = fragmentTag

        if (selectedItemId == graphId){
            selectedNavController.value = fragment.navController
            fragmentManager.firstAttachFragment(fragment, index == 0)
        }
        else fragmentManager.detachFragment(fragment)
    }

    var currentItemTag = mapGraphIdToTag[selectedItemId]
    val firstFragmentTag = mapGraphIdToTag[firstFragmentGraphId]
    var isOnFirstFragment = currentItemTag == firstFragmentTag

    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = mapGraphIdToTag[item.itemId]
            if (currentItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                // Exclude the first fragment tag because it's always in the back stack.
                if (firstFragmentTag != newlySelectedItemTag) {
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    fragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.nav_default_enter_anim,
                            R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim
                        )
                        .attach(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            // Detach all other Fragments
                            mapGraphIdToTag.forEach { _, fragmentTagIter ->
                                if (fragmentTagIter != newlySelectedItemTag) {
                                    detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                }
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }
                currentItemTag = newlySelectedItemTag
                isOnFirstFragment = currentItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
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

private fun FragmentManager.obtainNavHostFragment(
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    val currentFragment = findFragmentByTag(fragmentTag) as NavHostFragment?
    currentFragment?.let { return it }

    val newFragment = NavHostFragment.create(navGraphId)
    addFragment(newFragment, fragmentTag, containerId)
    return newFragment
}

fun FragmentManager.firstAttachFragment(fragment: Fragment, isPrimaryFragment: Boolean) {
    beginTransaction()
        .attach(fragment)
        .apply {
            if (isPrimaryFragment) {
                setPrimaryNavigationFragment(fragment)
            }
        }
        .commitNow()
}

fun FragmentManager.detachFragment(fragment: Fragment) {
    beginTransaction()
        .detach(fragment)
        .commitNow()
}

fun FragmentManager.addFragment(fragment: Fragment, fragmentTag: String, containerId: Int) {
    beginTransaction().add(containerId, fragment, fragmentTag).commitNow()
}

private fun getFragmentTag(itemId: Int): String = "FragmentTag$itemId"