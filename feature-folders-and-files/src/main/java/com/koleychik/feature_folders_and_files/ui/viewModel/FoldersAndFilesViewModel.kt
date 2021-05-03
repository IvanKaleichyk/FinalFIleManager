package com.koleychik.feature_folders_and_files.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.FilesCoreConstants.ROOT_PATH
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.FileCarcass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoldersAndFilesViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val fullList = MutableLiveData<List<FileCarcass>>(null)
    private val _currentList = MutableLiveData<List<FileCarcass>>(null)
    val currentList: LiveData<List<FileCarcass>> get() = _currentList

    private var jobGetFoldersAndFiles: Job? = null
    private var jobGetFirstTimeGetFoldersAndFiles: Job? = null
    private var jobSearching: Job? = null

    fun search(searchWord: String?) {
        jobSearching?.cancel()
        jobSearching = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            var newCurrentList: List<FileCarcass> = emptyList()
            fullList.value?.let { list -> newCurrentList = list.searchByName(searchWord) }
            withContext(Dispatchers.Main) {
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

    fun getFirstTimeGetFoldersAndFiles(path: String, word: String?) {
        if (jobGetFirstTimeGetFoldersAndFiles != null && jobGetFirstTimeGetFoldersAndFiles?.isActive == true) return

        jobGetFirstTimeGetFoldersAndFiles = viewModelScope.launch(Dispatchers.IO) {
            getFoldersAndFiles(path, word)
        }
    }

    fun openFile(model: FileCarcass) = repository.openFile(model)

    fun getFoldersAndFiles(path: String = ROOT_PATH, searchWord: String?) {
        jobGetFoldersAndFiles?.cancel()

        jobGetFoldersAndFiles = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullList = repository.getFoldersAndFiles(path)
            val newCurrentList =
                if (searchWord == null || searchWord.isEmpty()) newFullList
                else newFullList.searchByName(searchWord)

            withContext(Dispatchers.Main) {
                fullList.value = newFullList
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }
}