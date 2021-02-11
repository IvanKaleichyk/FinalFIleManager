package com.koleychik.feature_music

import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koleychik.models.fileCarcass.MusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicViewModel @Inject constructor() : ViewModel() {

    val list = MutableLiveData<List<MusicModel>>(null)

    val currentAudio = MutableLiveData<MediaMetadataCompat>(null)

    val isPlaying = MutableLiveData(false)

    fun load() = viewModelScope.launch(Dispatchers.IO) {
//        val newList = repository.getNewList()
        withContext(Dispatchers.Main) {
//            list.value = newList
        }
    }

    fun setData(model: MusicModel) {

    }

}