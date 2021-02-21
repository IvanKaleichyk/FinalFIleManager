package com.koleychik.feature_music.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.feature_searching_impl.framework.searchByName
import com.koleychik.models.fileCarcass.MusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MusicViewModel @Inject constructor(private val repository: FilesRepository) : ViewModel() {

    val fullList = MutableLiveData<List<MusicModel>>(null)
    val currentList = MutableLiveData<List<MusicModel>>(null)

//    val currentAudio = MutableLiveData<MediaMetadataCompat>(null)

    val isPlaying = MutableLiveData(false)

    fun getMusic(word: String?) = viewModelScope.launch(Dispatchers.IO) {
        val newFullList = repository.getMusic()
        val newCurrentList: List<MusicModel> = if (word == null || word.isEmpty()) newFullList
        else newFullList.searchByName(word)

        withContext(Dispatchers.Main) {
            fullList.value = newFullList
            currentList.value = newCurrentList
        }
    }

    fun search(word: String) = viewModelScope.launch(Dispatchers.IO) {
        var newCurrentList: List<MusicModel> = emptyList()
        fullList.value?.let { fullList ->
            newCurrentList = fullList.searchByName(word)
        }
        withContext(Dispatchers.Main) {
            currentList.value = newCurrentList
        }
    }

}