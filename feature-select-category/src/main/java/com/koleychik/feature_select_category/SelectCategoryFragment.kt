package com.koleychik.feature_select_category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.koleychik.feature_select_category.databinding.FragmentSelectCategoryBinding
import com.koleychik.models.CategoryModel
import javax.inject.Inject

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: SelectCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCategoryBinding.inflate(layoutInflater, container, false)
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

}