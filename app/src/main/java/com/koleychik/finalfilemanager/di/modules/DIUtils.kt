package com.koleychik.finalfilemanager.di.modules

import android.util.SparseArray
import androidx.core.util.set
import androidx.navigation.fragment.NavHostFragment
import com.koleychik.finalfilemanager.R

fun createNavHostFragmentSparseArray(): SparseArray<NavHostFragment> =
    SparseArray<NavHostFragment>().apply {
        this[R.id.files] = NavHostFragment.create(R.navigation.files)
        this[R.id.folders] = NavHostFragment.create(R.navigation.folders)
    }

