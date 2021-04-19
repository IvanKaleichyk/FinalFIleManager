package com.koleychik.feature_select_category.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_select_category.databinding.FragmentSelectCategoryBinding
import com.koleychik.feature_select_category.di.SelectCategoryComponentHolder
import com.koleychik.injector.AppConstants.TAG
import com.koleychik.injector.NavigationSystem
import com.koleychik.models.CategoryModel
import javax.inject.Inject

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null

    private val binding get() = _binding!!

    @Inject
    internal lateinit var adapter: SelectCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "create SelectCategoryFragment")
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentSelectCategoryBinding.inflate(layoutInflater, container, false)
        SelectCategoryComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRv()
    }

    private fun createRv() {
        with(binding.rv) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@SelectCategoryFragment.adapter
        }
        adapter.submitList(getData())
    }

    private fun getData() = listOf(
        CategoryModel.IMAGE,
        CategoryModel.VIDEO,
        CategoryModel.MUSIC,
        CategoryModel.DOCUMENT
    )


    override fun onStop() {
        super.onStop()
        SelectCategoryComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}