package com.koleychik.feature_searching_impl.framework

import com.koleychik.feature_searching_api.SearchingApi
import com.koleychik.models.fileCarcass.FileCarcass
import javax.inject.Inject

class SearchingImpl<T : FileCarcass> @Inject constructor() : SearchingApi<T> {


    override fun searchByName(value: String?): List<T> {
        currentList = if (value == "" || value == null) fullList
        else fullList.filter { it.name.contains(value, true) }
        return currentList
    }

    override fun getCurrentList(): List<T> = currentList

    override fun getFullList(): List<T> = fullList

    override fun setFullList(newList: List<T>) {
        fullList = newList
    }
}