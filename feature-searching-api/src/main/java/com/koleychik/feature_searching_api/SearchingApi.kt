package com.koleychik.feature_searching_api

import com.koleychik.models.fileCarcass.FileCarcass

interface SearchingApi {

    fun <T : FileCarcass> searchByName(fullList: List<T>, value: String?): List<T>
}