package com.koleychik.feature_rv_files_impl.framework

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import coil.Coil
import coil.ImageLoader
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
        list.clear()
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

            loadIcon(model)
            with(binding) {
                name.text = model.name
                size.text = if (model is FolderModel) "" else model.sizeAbbreviation
                root.setOnClickListener {
                    if (model is FolderModel) onClick?.let { click -> click(model, position) }
                    else openFile(model)
                }
            }
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

        private fun openFile(model: FileCarcass) {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = model.uri
//            val intentOpen = Intent.createChooser(intent, "Choose an application to open with:")
//            ContextCompat.startActivity(
//                binding.root.context.applicationContext,
//                intentOpen,
//                Bundle()
//            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = model.uri
            val intentOpen = Intent.createChooser(intent, "Choose an application to open with:")
            intentOpen.flags = FLAG_ACTIVITY_NEW_TASK

            binding.root.context.startActivity(
//                binding.root.context.applicationContext,
                intentOpen,
                Bundle()
            )

        }

    }
}