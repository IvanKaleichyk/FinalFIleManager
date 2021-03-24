package com.koleychik.feature_documents.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.feature_searching_impl.framework.searchByType
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.document.DocumentModel
import com.koleychik.models.fileCarcass.document.DocumentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DocumentsViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {


    private val fullListWithFormats = MutableLiveData<List<DocumentModel>>(null)
    val fullList = MutableLiveData<List<DocumentModel>>(null)
    val currentList = MutableLiveData<List<DocumentModel>>(null)

    val listSelectFormats = MutableLiveData(
        mutableListOf(
            DocumentType.TXT,
            DocumentType.PDF,
            DocumentType.EPUB,
            DocumentType.B2,
            DocumentType.DOC,
            DocumentType.DOCX,
            DocumentType.PPT,
        )
    )

    fun openFile(model: FileCarcass) {
        repository.openFile(model)
    }

    fun search(word: String) = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList: List<DocumentModel> = emptyList()
        fullList.value?.let { fullList ->
            newCurrentList = fullList.searchByName(word)
        }
        withContext(Dispatchers.Main) {
            currentList.value = newCurrentList
        }
    }

    fun setFullList(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newFullList = fullListWithFormats.value?.searchByType(listSelectFormats.value)
        withContext(Dispatchers.Main) {
            newFullList?.let { newFullList -> fullList.value = newFullList }
            setCurrentList(word)
        }
    }

    private fun setCurrentList(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newCurrentList = if (word == null || word.isEmpty()) fullList.value
        else fullList.value?.searchByName(word)
        withContext(Dispatchers.Main) {
            newCurrentList?.let { newCurrentList -> currentList.value = newCurrentList }
        }
    }

    fun getDocuments(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newFullListWithFormats = repository.getDocuments()
        val newFullList = newFullListWithFormats.searchByType(listSelectFormats.value)
        val newCurrentList: List<DocumentModel> = if (word == null || word.isEmpty()) newFullList
        else newFullList.searchByName(word)

        withContext(Dispatchers.Main) {
            fullListWithFormats.value = newFullListWithFormats
            fullList.value = newFullList
            currentList.value = newCurrentList
        }
    }

    fun delete(model: FileCarcass) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(model)
    }
}