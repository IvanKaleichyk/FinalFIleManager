package com.koleychik.feature_select_category.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_select_category.databinding.FragmentSelectCategoryBinding
import com.koleychik.feature_select_category.di.SelectCategoryComponentHolder
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationComponentHolder
import com.koleychik.models.CategoryModel

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null

    private val binding get() = _binding!!


    private val adapter = SelectCategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryBinding.inflate(layoutInflater, container, false)
        SelectCategoryComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRv()
    }

    private fun createRv() {
        with(binding) {
            rv.layoutManager = GridLayoutManager(requireContext(), 2)
            rv.adapter = adapter
        }
        adapter.submitList(getData())
    }

    private fun getData() = listOf(
        CategoryModel.ImageCategory,
        CategoryModel.VideoCategory,
        CategoryModel.MusicCategory,
        CategoryModel.DownloadsCategory
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        SelectCategoryComponentHolder.reset()
        SelectCategoryNavigationComponentHolder.reset()
    }
}