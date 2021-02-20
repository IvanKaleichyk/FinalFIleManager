package com.koleychik.feature_images.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val repository: FilesRepository,
    private val searchingApi: SearchingApi
) : ViewModel() {

    val fullList = MutableLiveData<List<ImageModel>>(null)
    val currentList = MutableLiveData<List<ImageModel>>(null)

    val searchingWord = MutableLiveData<String>(null)

    fun search() = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList = listOf<ImageModel>()
        fullList.value?.let { fullList ->
            newCurrentList = searchingApi.searchByName(fullList, searchingWord.value)
        }
        withContext(Dispatchers.Main) {
            fullList.value = newCurrentList
        }
    }

    fun getImages() = viewModelScope.launch(Dispatchers.IO) {
        val newFullList = repository.getImages()
        val newCurrentList = searchingApi.searchByName(newFullList, searchingWord.value)

        withContext(Dispatchers.Main) {
            fullList.value = newFullList
            currentList.value = newCurrentList
        }
    }
}