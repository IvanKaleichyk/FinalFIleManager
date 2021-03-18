package com.koleychik.feature_nav_bar.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.feature_nav_bar.databinding.FragmentNavBarBinding
import com.koleychik.feature_nav_bar.di.NavBarFeatureComponentHolder
import com.koleychik.injector.NavigationSystem
import javax.inject.Inject

class NavBarFragment : Fragment() {

    private var _binding: FragmentNavBarBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var navHosts: NavBarUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentNavBarBinding.inflate(inflater, container, false)
        NavBarFeatureComponentHolder.getComponent().inject(this)

//        requireActivity().findNavController(R.id.bottom_navController)
//            .addOnDestinationChangedListener { _, destination, _ ->
//                navHosts.onDestinationChange(destination.id)
//            }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNav.setup(childFragmentManager, navHosts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        NavBarFeatureComponentHolder.reset()
    }
}