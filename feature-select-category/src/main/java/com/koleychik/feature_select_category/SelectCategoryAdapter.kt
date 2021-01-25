package com.koleychik.feature_select_category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_select_category.databinding.ItemRvSelectCategoryBinding
import com.koleychik.models.CategoryModel

class SelectCategoryAdapter : RecyclerView.Adapter<SelectCategoryAdapter.MainViewHolder>() {

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

    class MainViewHolder(private val binding: ItemRvSelectCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoryModel) {
            binding.icon.setImageResource(model.imgRes)
            binding.name.setText(model.name)

            binding.cardView.setOnClickListener {
                when (model.id) {
//                    0 -> navigation.navigate(R.id.action_selectCategoryFragment_to_imagesFragment)
//                    1 -> navigation.navigate(R.id.)
//                    2 -> navigation.navigate(R.id.)
//                    3 -> navigation.navigate(R.id.action_selectCategoryFragment_to_documentsFragment)
//                    4 -> navigation.navigate(R.id.)
                }
            }
        }
    }
}