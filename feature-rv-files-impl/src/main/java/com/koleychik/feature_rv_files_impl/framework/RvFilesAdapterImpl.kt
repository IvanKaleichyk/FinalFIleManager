package com.koleychik.feature_rv_files_impl.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterViewHolder
import com.koleychik.feature_rv_files_impl.databinding.ItemRvFilesLayoutBinding
import com.koleychik.models.fileCarcass.FileCarcass

internal class RvFilesAdapterImpl : RvFilesAdapterApi() {

    private val list: SortedList<FileCarcass>

    init {
        list = SortedList(
            FileCarcass::class.java,
            object : SortedListAdapterCallback<FileCarcass>(this) {
                override fun compare(o1: FileCarcass, o2: FileCarcass): Int = o1.weight - o2.weight

                override fun areContentsTheSame(
                    oldItem: FileCarcass,
                    newItem: FileCarcass
                ): Boolean =
                    oldItem.dateAdded == newItem.dateAdded

                override fun areItemsTheSame(item1: FileCarcass, item2: FileCarcass): Boolean =
                    item1 == item2
            })
    }

    override fun submitList(newList: List<FileCarcass>) {
        list.addAll(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvFilesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RvFilesAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size()

    class MainViewHolder(binding: ItemRvFilesLayoutBinding) :
        RvFilesAdapterViewHolder(binding.root) {

        override fun bind(model: FileCarcass) {

        }

    }
}