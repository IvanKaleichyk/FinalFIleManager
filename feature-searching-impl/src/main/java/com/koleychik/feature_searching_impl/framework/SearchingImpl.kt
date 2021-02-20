package com.koleychik.feature_searching_impl.framework

import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.models.fileCarcass.FileCarcass
import javax.inject.Inject

class SearchingImpl @Inject constructor() : SearchingApi {

    override fun <T : FileCarcass> searchByName(fullList: List<T>, value: String?): List<T> {
        return if (value == null || value == "") fullList
        else fullList.filter { it.name.contains(value, true) }
    }
}