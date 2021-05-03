package com.koleychik.feature_video.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.media.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val repository: FilesRepository) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val fullList = MutableLiveData<List<VideoModel>>(null)
    private val _currentList = MutableLiveData<List<VideoModel>>(null)
    val currentList: LiveData<List<VideoModel>> get() = _currentList

    private var jobGetFirstTimeVideoData: Job? = null
    private var jobGetVideo: Job? = null
    private var jobSearching: Job? = null

    fun getFirstTimeVideoData(word: String?) {
        if (jobGetFirstTimeVideoData != null && jobGetFirstTimeVideoData?.isActive == true) return

        jobGetFirstTimeVideoData = viewModelScope.launch(Dispatchers.IO) {
            getVideo(word)
        }
    }

    fun getVideo(word: String?) {
        jobGetVideo?.cancel()
        jobGetVideo = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullList = repository.getVideo()
            val newCurrentList: List<VideoModel> = if (word == null || word.isEmpty()) newFullList
            else newFullList.searchByName(word)

            withContext(Dispatchers.Main) {
                fullList.value = newFullList
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

    fun search(word: String?) {
        jobSearching?.cancel()
        jobSearching = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            var newCurrentList: List<VideoModel> = emptyList()
            fullList.value?.let { fullList ->
                newCurrentList = fullList.searchByName(word)
            }
            withContext(Dispatchers.Main) {
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

}