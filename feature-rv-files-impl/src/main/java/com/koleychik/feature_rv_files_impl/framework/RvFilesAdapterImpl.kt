package com.koleychik.feature_rv_files_impl.framework

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.Coil
import coil.ImageLoader
import coil.clear
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import coil.size.Scale
import com.koleychik.feature_rv_documents_api.RvFilesAdapterApi
import com.koleychik.feature_rv_documents_api.RvFilesAdapterViewHolder
import com.koleychik.feature_rv_files_impl.R
import com.koleychik.feature_rv_files_impl.databinding.ItemRvFilesLayoutBinding
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.FolderModel
import com.koleychik.models.type.FileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class RvFilesAdapterImpl @Inject constructor() : RvFilesAdapterApi() {

    private val list = mutableListOf<FileCarcass>()

    override fun submitList(newList: List<FileCarcass>) {
        val diffUtil = RvFilesDiffUtils(newList = newList, oldList = list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override var onClick: ((model: FileCarcass, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemRvFilesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RvFilesAdapterViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: RvFilesAdapterViewHolder) {
        super.onViewRecycled(holder)
        holder.clearImage()
    }

    inner class MainViewHolder(private val binding: ItemRvFilesLayoutBinding) :
        RvFilesAdapterViewHolder(binding.root) {

        override fun bind(model: FileCarcass, position: Int) {

            loadIcon(model)
            with(binding) {
                name.text = model.name
                size.text = if (model is FolderModel) "" else model.sizeAbbreviation
                root.setOnClickListener {
                    onClick?.let { click -> click(model, position) }
                }
            }
        }

        override fun clearImage() {
            binding.icon.clear()
        }

        private fun loadIcon(model: FileCarcass) {
            when (model.type) {
                is FileType.ImageType -> loadImage(model.uri, model.type.imgRes)
                is FileType.VideoType -> loadVideoPreview(model.uri, model.type.imgRes)
                else -> binding.icon.setImageResource(model.type.imgRes)
            }
        }

        private fun loadImage(uri: Uri, imagePlaceholder: Int) {
            val context = binding.root.context
            binding.icon.load(uri) {
                placeholder(imagePlaceholder)
                crossfade(true)
                allowRgb565(true)
                size(
                    context.resources.getDimensionPixelSize(R.dimen.card_width),
                    context.resources.getDimensionPixelSize(R.dimen.card_width)
                )
                scale(Scale.FILL)
            }
        }

        private fun loadVideoPreview(uri: Uri, imagePlaceholder: Int) =
            CoroutineScope(Dispatchers.Default).launch {
                val context = binding.root.context
                val imageLoader = ImageLoader.Builder(context)
                    .componentRegistry {
                        add(VideoFrameFileFetcher(context))
                        add(VideoFrameUriFetcher(context))
                    }
                    .build()
                Coil.setImageLoader(imageLoader)

                val request = ImageRequest.Builder(context)
                    .placeholder(imagePlaceholder)
                    .data(uri)
                    .videoFrameMillis(2000)
                    .target(binding.icon)
                    .fetcher(VideoFrameUriFetcher(context))
                    .build()

                imageLoader.execute(request)
            }
    }
}