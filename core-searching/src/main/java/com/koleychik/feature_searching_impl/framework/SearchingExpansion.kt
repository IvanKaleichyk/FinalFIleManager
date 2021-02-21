package com.koleychik.feature_searching_impl.framework

import com.koleychik.models.fileCarcass.FileCarcass

fun <T : FileCarcass> List<T>.searchByName(value: String?): List<T> {
    return if (value == null || value == "") this
    else filter { it.name.contains(value, true) }
}