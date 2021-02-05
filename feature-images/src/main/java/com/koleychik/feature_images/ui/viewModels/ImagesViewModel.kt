package com.koleychik.feature_images.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ImagesViewModel(private val repository: FilesRepository) : ViewModel() {

    val list = MutableLiveData<List<ImageModel>>(null)

    fun getImages() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getImages()
        withContext(Dispatchers.Main) {
            list.value = newList
        }
    }
}