package com.koleychik.feature_images.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val repository: FilesRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val fullList = MutableLiveData<List<ImageModel>>(null)
    private val _currentList = MutableLiveData<List<ImageModel>>(null)
    val currentList: LiveData<List<ImageModel>> get() = _currentList

    private var jobGetFirstTimeImagesData: Job? = null
    private var jobGetImages: Job? = null
    private var jobSearching: Job? = null

    fun search(word: String?) {
        jobSearching?.cancel()
        jobSearching = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            var newCurrentList: List<ImageModel> = emptyList()
            fullList.value?.let { fullList ->
                newCurrentList = fullList.searchByName(word)
            }
            withContext(Dispatchers.Main) {
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

    fun getFirstTimeImagesData(word: String?) {
        if (jobGetFirstTimeImagesData != null && jobGetFirstTimeImagesData?.isActive == true) return

        jobGetFirstTimeImagesData = viewModelScope.launch(Dispatchers.IO) {
            getImages(word)
        }
    }

    fun getImages(word: String?) {
        jobGetImages?.cancel()
        jobGetImages = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullList = repository.getImages()
            val newCurrentList: List<ImageModel> =
                if (word == null || word.isEmpty()) newFullList
                else newFullList.searchByName(word)

            withContext(Dispatchers.Main) {
                fullList.value = newFullList
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }
}