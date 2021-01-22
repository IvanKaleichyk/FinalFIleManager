package com.koleychik.feature_rv_common_impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_rv_common_api.FeatureRvMediaAdapterApi
import com.koleychik.feature_rv_common_impl.databinding.ItemRvMediaPriviewBinding
import com.koleychik.models.fileCarcass.media.MediaCarcass

class FeatureRvMediaAdapterImpl :
    FeatureRvMediaAdapterApi<FeatureRvMediaAdapterImpl.MainViewHolder>() {

    private val list = mutableListOf<MediaCarcass>()

    override fun submitList(newList: List<MediaCarcass>) {
        val diffUtil = MediaDiffUtil(newList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvMediaPriviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class MainViewHolder(binding: ItemRvMediaPriviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MediaCarcass) {

        }
    }
}