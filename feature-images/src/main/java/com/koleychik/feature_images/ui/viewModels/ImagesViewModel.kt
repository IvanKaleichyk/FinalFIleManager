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

internal class ImagesViewModel @Inject constructor(
    private val repository: FilesRepository,
    private val searchingApi: SearchingApi<ImageModel>
) :
    ViewModel() {

    val list = MutableLiveData<List<ImageModel>>(null)

    val searchingWord = MutableLiveData<String>(null)

    fun search(value: String) = viewModelScope.launch(Dispatchers.IO) {
        val newList = searchingApi.searchByName(value)
        withContext(Dispatchers.Main){
            list.value =
        }
    }

    fun getImages() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getImages()

        searchingApi.run {
            setFullList(newList)
            searchByName(searchingWord.value)
        }

        withContext(Dispatchers.Main) {
            list.value = newList
        }
    }
}