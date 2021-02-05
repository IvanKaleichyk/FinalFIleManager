package com.koleychik.feature_rv_common_impl.framework

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.clear
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.koleychik.feature_rv_common_api.RvMediaAdapterApi
import com.koleychik.feature_rv_common_api.RvMediaViewHolderApi
import com.koleychik.feature_rv_common_impl.R
import com.koleychik.feature_rv_common_impl.databinding.ItemRvMediaPriviewBinding
import com.koleychik.models.fileCarcass.media.MediaCarcass

internal class RvMediaAdapterImpl : RvMediaAdapterApi() {

    private val list = mutableListOf<MediaCarcass>()

    private var onCLick: ((model: MediaCarcass, position: Int) -> Unit)? = null

    override fun setOnCLick(onCLick: ((model: MediaCarcass, position: Int) -> Unit)?) {
        this.onCLick = onCLick
    }

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

    override fun onBindViewHolder(holder: RvMediaViewHolderApi, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: RvMediaViewHolderApi) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    inner class MainViewHolder(var _binding: ItemRvMediaPriviewBinding?) :
        RvMediaViewHolderApi(_binding!!.root) {

        private val binding get() = _binding!!

        override fun bind(model: MediaCarcass, position: Int) {
            loadImage(model.uri)
            with(binding) {
                nameImage.text = model.name
                size.text = model.sizeAbbreviation
            }
            createSetOnClickListener(model, position)
        }

        private fun createSetOnClickListener(model: MediaCarcass, position: Int) {
            onCLick?.let { click ->
                binding.root.setOnClickListener {
                    click(model, position)
                }
            }

        }

        private fun loadImage(uri: Uri) {
            val context = binding.root.context
            binding.image.load(uri) {
                crossfade(true)
                allowRgb565(true)
                size(
                    context.resources.getDimensionPixelSize(R.dimen.card_width),
                    context.resources.getDimensionPixelSize(R.dimen.card_width)
                )
                transformations(CircleCropTransformation())
                scale(Scale.FILL)
            }
        }

        override fun clear() {
            binding.image.clear()
            _binding = null
        }
    }
}