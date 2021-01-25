package com.koleychik.feature_rv_common_impl

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.koleychik.feature_rv_common_api.FeatureRvMediaAdapterApi
import com.koleychik.feature_rv_common_api.FeatureRvMediaViewHolderApi
import com.koleychik.feature_rv_common_impl.databinding.ItemRvMediaPriviewBinding
import com.koleychik.models.fileCarcass.media.MediaCarcass
import okhttp3.internal.cache.CacheStrategy

class FeatureRvMediaAdapterImpl :
    FeatureRvMediaAdapterApi() {

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

    override fun onBindViewHolder(holder: FeatureRvMediaViewHolderApi, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: FeatureRvMediaViewHolderApi) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    class MainViewHolder(var _binding: ItemRvMediaPriviewBinding?) :
        FeatureRvMediaViewHolderApi(_binding!!.root) {

        private val binding get() = _binding!!

        override fun bind(model: MediaCarcass) {
            loadImage(model.uri)
            with(binding){
                nameImage.text = model.name
                size.text = model.sizeAbbreviation
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