package com.koleychik.feature_searching_api

import com.koleychik.models.fileCarcass.FileCarcass

interface SearchingApi<T : FileCarcass> {

    fun searchByName(value: String?): List<T>
    fun getCurrentList(): List<T>
    fun getFullList(): List<T>
    fun setFullList(newList: List<T>)

}