package com.koleychik.feature_video.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.media.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val repository: FilesRepository) : ViewModel() {

    val fullList = MutableLiveData<List<VideoModel>>(null)
    val currentList = MutableLiveData<List<VideoModel>>(null)

    fun getVideo(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newFullList = repository.getVideo()
        val newCurrentList: List<VideoModel> = if (word == null || word.isEmpty()) newFullList
        else newFullList.searchByName(word)

        withContext(Dispatchers.Main) {
            fullList.value = newFullList
            currentList.value = newCurrentList
        }
    }

    fun search(word: String) = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList: List<VideoModel> = emptyList()
        fullList.value?.let { fullList ->
            newCurrentList = fullList.searchByName(word)
        }
        withContext(Dispatchers.Main) {
            currentList.value = newCurrentList
        }
    }

}