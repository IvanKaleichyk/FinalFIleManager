package com.koleychik.feature_images.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val repository: FilesRepository
) : ViewModel() {

    val fullList = MutableLiveData<List<ImageModel>>(null)
    val currentList = MutableLiveData<List<ImageModel>>(null)

    fun search(word: String) = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList: List<ImageModel> = emptyList()
        fullList.value?.let { fullList ->
            newCurrentList = fullList.searchByName(word)
        }
        withContext(Dispatchers.Main) {
            currentList.value = newCurrentList
        }
    }

    fun getImages(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newFullList = repository.getImages()
        val newCurrentList: List<ImageModel> = if (word == null || word.isEmpty()) newFullList
        else newFullList.searchByName(word)

        withContext(Dispatchers.Main) {
            fullList.value = newFullList
            currentList.value = newCurrentList
        }
    }
}