package com.koleychik.feature_music

import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.core_media_player.service.MediaSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicViewModel(private val repository: MediaSource) : ViewModel() {

    val list = MutableLiveData<List<MediaMetadataCompat>>(null)

    val currentAudio = MutableLiveData<MediaMetadataCompat>(null)

    val isPlaying = MutableLiveData(false)

    fun load() = viewModelScope.launch(Dispatchers.IO) {
        val newList = repository.getNewList()
        withContext(Dispatchers.Main) {
            list.value = newList
        }
    }

    fun setData(model: MediaMetadataCompat) {

    }

}