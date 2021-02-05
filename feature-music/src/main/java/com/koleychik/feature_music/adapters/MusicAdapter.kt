package com.koleychik.feature_music.adapters

import android.support.v4.media.MediaMetadataCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.feature_music.databinding.ItemRvMusicBinding

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MainViewHolder>() {

    private val list = mutableListOf<MediaMetadataCompat>()

    var onClick: ((model: MediaMetadataCompat) -> Unit)? = null

    fun submitList(newList: List<MediaMetadataCompat>) {
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

        fun bind(model: MediaMetadataCompat) {
            with(binding) {
                textTitle.text = model.description.subtitle
                textAuthor.text = model.description.title
                textDuration.text = model.description.mediaDescription.toString()
                textSize.text = model.size().toString()
                binding.root.setOnClickListener {
                    onClick?.let { click -> click(model) }
                }
            }
        }

    }

}