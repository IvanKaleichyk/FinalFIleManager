package com.koleychik.feature_music.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_music.databinding.ItemRvMusicBinding
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.MusicModel

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MainViewHolder>() {

    private val list = mutableListOf<MusicModel>()

    var onClick: ((model: MusicModel) -> Unit)? = null

    fun submitList(newList: List<MusicModel>) {
        val diffUtil = MusicDiffUtil(newList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainViewHolder(private val binding: ItemRvMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MusicModel) {
            with(binding) {
                textTitle.text = model.title
                textAuthor.text = model.artist
                textDuration.text = model.duration.toString()
                textSize.text = model.sizeAbbreviation
                binding.root.setOnClickListener {
//                    onClick?.let { click -> click(model) }
                    openFile(model)
                }
            }
        }

        private fun openFile(model: FileCarcass) {

            val intent = Intent(Intent.ACTION_VIEW).apply { setDataAndType(model.uri, model.mimeType) }
            binding.root.context.startActivity(intent)

//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = model.uri
//            val intentOpen = Intent.createChooser(intent, "Choose an application to open with:")
//            intentOpen.type = model.mimeType
//            binding.root.context.startActivity(intentOpen)
//            ContextCompat.startActivity(
//                binding.root.context.applicationContext,
//                intentOpen,
//                Bundle()
//            )
        }
    }

}