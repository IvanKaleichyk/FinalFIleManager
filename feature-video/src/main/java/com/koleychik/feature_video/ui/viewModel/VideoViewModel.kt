package com.koleychik.feature_video.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.media.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val repository: FilesRepository) : ViewModel() {

    val list = MutableLiveData<List<VideoModel>>(null)

    fun getVideo() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getVideo()
        withContext(Dispatchers.Main) {
            list.value = newList
        }
    }

}