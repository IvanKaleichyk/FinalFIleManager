package com.koleychik.feature_documents.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.DocumentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DocumentsViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    val list = MutableLiveData<MutableList<DocumentModel>>(null)

    val fullList = MutableLiveData<List<DocumentModel>>()


    val listTypes = listOf(
        DocumentType.TXT,
        DocumentType.PDF,
        DocumentType.EPUB,
        DocumentType.B2,
        DocumentType.DOC,
        DocumentType.DOCX,
        DocumentType.PPT,
        DocumentType.OTHER
    )

    fun addToListDocumentsWithType(type: DocumentType) = viewModelScope.launch(Dispatchers.IO) {
        val newList = mutableListOf<DocumentModel>()
        if (fullList.value != null) {
            for (i in fullList.value!!) if (i.getDocumentType().id == type.id) newList.add(i)
        }
        list.value?.addAll(newList)
    }

    fun deleteDocumentsWithType(type: DocumentType) = viewModelScope.launch(Dispatchers.IO) {
        val newList = mutableListOf<DocumentModel>()
        if (list.value != null) {
            for (i in list.value!!) if (i.getDocumentType().id != type.id) newList.add(i)
            list.value = newList
        }
    }

    fun getDocuments() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getDocuments()
        withContext(Dispatchers.Main) {
            fullList.value = newList
            for (i in listTypes) if (i.isSelected) addToListDocumentsWithType(i)
        }
    }

    fun delete(model: FileCarcass) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(model)
    }
}