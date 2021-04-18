package com.koleychik.feature_nav_bar.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_nav_bar.R
import com.koleychik.feature_nav_bar.databinding.FragmentNavBarBinding
import com.koleychik.feature_nav_bar.di.NavBarFeatureComponentHolder
import com.koleychik.injector.NavigationSystem
import javax.inject.Inject

class NavBarFragment : Fragment() {

    private var _binding: FragmentNavBarBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var navHosts: NavBarUtils

    var isViewWasReset = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentNavBarBinding.inflate(inflater, container, false)
        NavBarFeatureComponentHolder.getComponent().inject(this)

        if (savedInstanceState == null) {
            navHosts.setup(binding.bottomNav, childFragmentManager, R.id.bottom_navController)
        } else isViewWasReset = true


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (isViewWasReset) {
            navHosts.setup(binding.bottomNav, childFragmentManager, R.id.bottom_navController)
        }
    }

    override fun onStop() {
        super.onStop()
        NavBarFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}