package com.koleychik.feature_documents.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.DocumentModel
import com.koleychik.models.fileCarcass.FileCarcass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DocumentsViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    val list = MutableLiveData<List<DocumentModel>>(null)

    fun getDocuments() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getDocuments()
        withContext(Dispatchers.Main) {
            list.value = newList
        }
    }

    fun delete(model: FileCarcass) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(model)
    }

}