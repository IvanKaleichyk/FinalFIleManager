package com.koleychik.feature_documents.ui.viewModels

import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DocumentsViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val fullListWithFormats = MutableLiveData<List<DocumentModel>>(null)

    private val fullList = MutableLiveData<List<DocumentModel>>(null)
    private val _currentList = MutableLiveData<List<DocumentModel>>(null)
    val currentList: LiveData<List<DocumentModel>> get() = _currentList

    private var jobSearching: Job? = null
    private var jobGetDocumentModels: Job? = null
    private var jobGetFirstTimeDocumentModelsData: Job? = null
    private var jobSetFullList: Job? = null

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

    fun getFirstTimeDocumentModelsData(word: String?) {
        if (jobGetFirstTimeDocumentModelsData != null && jobGetFirstTimeDocumentModelsData?.isActive == true) return

        jobGetFirstTimeDocumentModelsData = viewModelScope.launch(Dispatchers.IO) {
            getDocuments(word)
        }

    }

    fun search(word: String?) {
        jobSearching?.cancel()
        jobSearching = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            var newCurrentList: List<DocumentModel> = emptyList()
            fullList.value?.let { fullList ->
                newCurrentList = fullList.searchByName(word)
            }
            withContext(Dispatchers.Main) {
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

    fun setFullList(word: String?) {
        jobSetFullList?.cancel()
        jobSetFullList = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullList = fullListWithFormats.value?.searchByType(listSelectFormats.value)
            withContext(Dispatchers.Main) {
                newFullList?.let { newFullList -> fullList.value = newFullList }
                setCurrentList(word)
            }
        }
    }

    private suspend fun setCurrentList(word: String?) {
        val newCurrentList = if (word == null || word.isEmpty()) fullList.value
        else fullList.value?.searchByName(word)
        withContext(Dispatchers.Main) {
            newCurrentList?.let { newCurrentList -> _currentList.value = newCurrentList }
            _isLoading.value = false
        }
    }

    fun getDocuments(word: String?) {
        jobGetDocumentModels?.cancel()
        jobGetDocumentModels = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullListWithFormats = repository.getDocuments()
            val newFullList = newFullListWithFormats.searchByType(listSelectFormats.value)
            val newCurrentList: List<DocumentModel> =
                if (word == null || word.isEmpty()) newFullList
                else newFullList.searchByName(word)

            withContext(Dispatchers.Main) {
                fullListWithFormats.value = newFullListWithFormats
                fullList.value = newFullList
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }
}