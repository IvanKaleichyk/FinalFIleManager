package com.koleychik.core_media_player.service

import android.support.v4.media.MediaMetadataCompat
import com.koleychik.core_files.api.FilesRepository
import com.koleychik.models.fileCarcass.MusicModel

class MediaSource(private val repository: FilesRepository) {

    var list: List<MusicModel> = emptyList()

    var currentMetadataPosition: Int? = null

    fun getNewList(): List<MusicModel> {
        list = repository.getMusic()
        return list
    }

    fun getCurrentMetadata(): MusicModel? {
        if (currentMetadataPosition == null) return null
        return list[currentMetadataPosition!!]
    }

    fun getNext(): MusicModel {
        var newPosition = currentMetadataPosition ?: -1
        newPosition++
        if (newPosition >= list.size) newPosition = 0
        currentMetadataPosition = newPosition
        return list[newPosition]
    }

    fun getPrevious(): MusicModel {
        var newPosition = currentMetadataPosition ?: -1
        newPosition--
        if (newPosition < 0) newPosition = list.size - 1
        currentMetadataPosition = newPosition
        return list[newPosition]
    }

}