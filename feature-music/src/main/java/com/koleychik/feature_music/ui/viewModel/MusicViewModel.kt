package com.koleychik.feature_music.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.FileCarcass
import com.koleychik.models.fileCarcass.MusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MusicViewModel @Inject constructor(private val repository: FilesRepository) :
    ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val fullList = MutableLiveData<List<MusicModel>>(null)
    private val _currentList = MutableLiveData<List<MusicModel>>(null)
    val currentList: LiveData<List<MusicModel>> get() = _currentList

    private var jobGetMusic: Job? = null
    private var jobGetFirstTimeMusic: Job? = null
    private var jobSearching: Job? = null

    fun openFile(model: FileCarcass) {
        repository.openFile(model)
    }

    fun getFirstTimeMusic(word: String?) {
        if (jobGetFirstTimeMusic != null && jobGetFirstTimeMusic?.isActive == true) return

        jobGetFirstTimeMusic = viewModelScope.launch {
            getMusic(word)
        }
    }

    fun getMusic(word: String?) {
        jobGetMusic?.cancel()
        jobGetMusic = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            val newFullList = repository.getMusic()
            val newCurrentList: List<MusicModel> =
                if (word == null || word.isEmpty()) newFullList
                else newFullList.searchByName(word)

            withContext(Dispatchers.Main) {
                fullList.value = newFullList
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

    fun search(word: String?) {
        jobSearching?.cancel()
        jobSearching = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _isLoading.value = true }
            var newCurrentList: List<MusicModel> = emptyList()
            fullList.value?.let { fullList ->
                newCurrentList = fullList.searchByName(word)
            }
            withContext(Dispatchers.Main) {
                _currentList.value = newCurrentList
                _isLoading.value = false
            }
        }
    }

}