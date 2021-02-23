package com.koleychik.feature_image_info

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun transformDateToDateFormat(dateTime: Long?): String {
    if (dateTime == null) return "Error"
    val pattern = "yyyy-MM-dd"
    val time = dateTime * 1000

    @SuppressLint("SimpleDateFormat")
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(Date(time))
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val date = LocalDate.ofEpochDay(time)
//        val formatter = DateTimeFormatter.ofPattern(pattern)
//        date.format(formatter)
//    } else {
//        @SuppressLint("SimpleDateFormat")
//        val simpleDateFormat = SimpleDateFormat(pattern)
//        simpleDateFormat.format(Date(time))
//    }
}