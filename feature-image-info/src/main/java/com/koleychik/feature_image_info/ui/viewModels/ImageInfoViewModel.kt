package com.koleychik.feature_image_info.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.media.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageInfoViewModel @Inject constructor(private val repository: FilesRepository) : ViewModel() {

    val currentImagePosition = MutableLiveData<Int>()

    fun deleteImage(model: ImageModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(model)
    }

}