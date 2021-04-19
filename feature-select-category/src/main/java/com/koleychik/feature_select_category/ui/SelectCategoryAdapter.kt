package com.koleychik.feature_select_category.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_select_category.SelectCategoryNavigationApi
import com.koleychik.feature_select_category.databinding.ItemRvSelectCategoryBinding
import com.koleychik.models.CategoryModel
import javax.inject.Inject

class SelectCategoryAdapter @Inject constructor(private val navigationApi: SelectCategoryNavigationApi) :
    RecyclerView.Adapter<SelectCategoryAdapter.MainViewHolder>() {

    private var list = listOf<CategoryModel>()

    fun submitList(list: List<CategoryModel>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvSelectCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainViewHolder(private val binding: ItemRvSelectCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoryModel) {
            binding.icon.setImageResource(model.imgRes)
            binding.name.setText(model.nameRes)

            binding.cardView.setOnClickListener {
                when (model.id) {
                    0 -> navigationApi.selectCategoryFeatureGoToImagesFragment(
                        Navigation.findNavController(
                            binding.root
                        )
                    )
                    1 -> navigationApi.selectCategoryFeatureGoToVideoFragment(
                        Navigation.findNavController(
                            binding.root
                        )
                    )
                    2 -> navigationApi.selectCategoryFeatureGoToMusicFragment(
                        Navigation.findNavController(
                            binding.root
                        )
                    )
                    3 -> navigationApi.selectCategoryFeatureGoToDocumentsFragment(
                        Navigation.findNavController(
                            binding.root
                        )
                    )
                }
            }
        }
    }
}