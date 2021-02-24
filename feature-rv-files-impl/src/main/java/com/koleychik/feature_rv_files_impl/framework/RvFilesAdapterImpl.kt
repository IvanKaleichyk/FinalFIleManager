package com.koleychik.feature_rv_files_impl.framework

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterViewHolder
import com.koleychik.feature_rv_files_impl.R
import com.koleychik.feature_rv_files_impl.databinding.ItemRvFilesLayoutBinding
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.FolderModel
import com.koleychik.models.fileCarcass.document.DocumentModel
import javax.inject.Inject

internal class RvFilesAdapterImpl @Inject constructor() : RvFilesAdapterApi() {

    private var onClick: ((model: FileCarcass, position: Int) -> Unit)? = null

    private val list: SortedList<FileCarcass> = SortedList(
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


    override fun submitList(newList: List<FileCarcass>) {
        list.addAll(newList)
    }

    override fun setOnClick(onClick: (model: FileCarcass, position: Int) -> Unit) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvFilesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RvFilesAdapterViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size()

    inner class MainViewHolder(private val binding: ItemRvFilesLayoutBinding) :
        RvFilesAdapterViewHolder(binding.root) {

        override fun bind(model: FileCarcass, position: Int) {
            val img =
                if (model is DocumentModel) model.imgRes else R.drawable.folder_icon_48_main_color

            with(binding) {
                icon.setImageResource(img)
                name.text = model.name
                size.text = model.sizeAbbreviation
                root.setOnClickListener {
                    if (model is FolderModel) onClick?.let { click -> click(model, position) }
                    else openFile(model)
                }
            }
        }

        private fun openFile(model: FileCarcass) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = model.uri
            val intentOpen = Intent.createChooser(intent, "Choose an application to open with:")
            ContextCompat.startActivity(binding.root.context, intentOpen, Bundle())
        }

    }
}