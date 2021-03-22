//package com.koleychik.feature_nav_bar.framework
//
//import android.util.Log
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import com.koleychik.feature_nav_bar.R
//import com.koleychik.injector.AppConstants.TAG
//
//
//fun BottomNavigationView.setup(
//    fragmentManager: FragmentManager,
//    navBarUtils: NavBarUtils
//) {
//    val mapFragment = fragmentManager.createMap(navBarUtils, selectedItemId)
//    var currentFragment =
//        fragmentManager.findFragmentByTag(getFragmentTag(selectedItemId))
//            ?: getFragmentByItemId(selectedItemId, mapFragment, navBarUtils)
//
//
//    setOnNavigationItemSelectedListener {
//        val newFragment: Fragment = fragmentManager.findFragmentByTag(getFragmentTag(it.itemId))
//            ?: getFragmentByItemId(it.itemId, mapFragment, navBarUtils)
//
//        if (newFragment != currentFragment) {
//            Log.d(TAG, "newFragment != currentFragment")
//            fragmentManager.detachFragment(currentFragment)
//
//            fragmentManager.attachFragment(newFragment)
//            currentFragment = newFragment
//        } else Log.d(TAG, "newFragment != currentFragment is False")
//
//        true
//    }
//}
//
//fun FragmentManager.createMap(navBarUtils: NavBarUtils, selectedItemId: Int): Map<Int, Fragment> {
//    val fileFragment = navBarUtils.fileNavHostFragment()
//    val folderFragment = navBarUtils.folderNavHostFragment()
//    val listId = listOf(R.id.files, R.id.folders)
//    val map = mapOf<Int, Fragment>(R.id.files to fileFragment, R.id.folders to folderFragment)
//    for (i in listId) {
//        val fragment = map[i] ?: continue
//        addFragment(fragment, i)
//        if (i == selectedItemId) attachFragment(fragment)
//        else detachFragment(fragment)
//    }
//    return map
//}
//
//fun FragmentManager.attachFragment(newFragment: Fragment) {
//    beginTransaction()
//        .setPrimaryNavigationFragment(newFragment)
//        .attach(newFragment)
//        .commit()
//}
//
//fun FragmentManager.addFragment(fragment: Fragment, id: Int) {
//    beginTransaction().add(R.id.bottom_navController, fragment, getFragmentTag(id)).commit()
//}
//
//fun FragmentManager.detachFragment(fragment: Fragment) {
//    Log.d(TAG, "detach Fragment")
//    beginTransaction().detach(fragment).commit()
//}
//
//private fun getFragmentByItemId(
//    id: Int,
//    map: Map<Int, Fragment>,
//    navBarUtils: NavBarUtils
//): Fragment {
//    return map[id] ?: navBarUtils.fileNavHostFragment()
//}
//
////fun getFragmentTag(index: Int): String = "fragmentTag$index"