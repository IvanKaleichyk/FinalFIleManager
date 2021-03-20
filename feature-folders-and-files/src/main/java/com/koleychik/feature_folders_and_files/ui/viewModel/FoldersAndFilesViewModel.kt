package com.koleychik.feature_folders_and_files.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.FilesCoreConstants.ROOT_PATH
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.FileCarcass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoldersAndFilesViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    val fullList = MutableLiveData<List<FileCarcass>>(null)
    val currentList = MutableLiveData<List<FileCarcass>>(null)

    fun search(searchWord: String?) = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList: List<FileCarcass> = emptyList()
        fullList.value?.let { list -> newCurrentList = list.searchByName(searchWord) }
        withContext(Dispatchers.Main) {
            currentList.value = newCurrentList
        }
    }

    fun getFoldersAndFiles(path: String = ROOT_PATH, searchWord: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            val newFullList = repository.getFoldersAndFiles(path)
//                if (path == null) repository.gelFilesFromFolder()
            val newCurrentList =
                if (searchWord == null || searchWord.isEmpty()) newFullList
                else newFullList.searchByName(searchWord)

            withContext(Dispatchers.Main) {
                fullList.value = newFullList
                currentList.value = newCurrentList
            }
        }
}