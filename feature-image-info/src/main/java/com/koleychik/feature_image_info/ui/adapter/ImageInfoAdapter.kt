package com.koleychik.feature_image_info.ui.adapter

import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.imageLoader
import coil.request.ImageRequest
import com.koleychik.feature_image_info.databinding.ImageSliderItemBinding
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageInfoAdapter : RecyclerView.Adapter<ImageInfoAdapter.MainViewHolder>() {

    var list: MutableList<ImageModel> = mutableListOf()
        private set

    fun submitList(newList: List<ImageModel>) {
        val diffUtil = ImageDiffUtil(newList, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ImageSliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: MainViewHolder) {
        super.onViewRecycled(holder)
        holder.destroy()
    }

    class MainViewHolder(private val binding: ImageSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var loader: Job? = null

        fun bind(model: ImageModel) {
            loader = loadImageUsingCoil(model)
        }

        fun destroy() {
            binding.sliderImage.clear()
            loader?.cancel()
        }

        private fun loadImageUsingCoil(model: ImageModel) =
            CoroutineScope(Dispatchers.Main).launch {
                val context = binding.root.context.applicationContext

                val request = ImageRequest.Builder(context)
                    .data(model.uri)
                    .target(
                        onStart = {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.sliderImage.visibility = View.INVISIBLE
                            binding.infoText.visibility = View.GONE
                        },
                        onSuccess = { result ->
                            binding.progressBar.visibility = View.GONE
                            binding.infoText.visibility = View.GONE
                            binding.sliderImage.run {
                                setImageBitmap((result as BitmapDrawable).bitmap)
                                visibility = View.VISIBLE
                            }
                        },
                        onError = { error ->
                            binding.progressBar.visibility = View.GONE
                            binding.sliderImage.visibility = View.INVISIBLE
                            binding.infoText.visibility = View.VISIBLE
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                        }
                    )
                    .build()
                context.imageLoader.execute(request)
            }
    }
}