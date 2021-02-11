package com.koleychik.feature_select_category.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_select_category.databinding.ItemRvSelectCategoryBinding
import com.koleychik.feature_select_category.navigation.SelectCategoryNavigationApi
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
            binding.name.setText(model.name)

            binding.cardView.setOnClickListener {
                when (model.id) {
                    0 -> navigationApi.selectCategoryFeatureGoToImagesFragment()
                    1 -> navigationApi.selectCategoryFeatureGoToVideoFragment()
                    2 -> navigationApi.selectCategoryFeatureGoToMusicFragment()
                    3 -> navigationApi.selectCategoryFeatureGoToDocumentsFragment()
                }
            }
        }
    }
}