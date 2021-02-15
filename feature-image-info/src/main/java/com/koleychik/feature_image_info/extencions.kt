package com.koleychik.feature_image_info

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun transformDateToDateFormat(dateTime: Long?): String {
    if (dateTime == null) return "Error"
    val pattern = "yyyy-MM-dd"
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val date = LocalDate.ofEpochDay(dateTime)
        val format = DateTimeFormatter.ofPattern(pattern)
        format.format(date)
    } else {
        @SuppressLint("SimpleDateFormat")
        val simpleDateFormat = SimpleDateFormat(pattern)
        simpleDateFormat.format(Date(dateTime))
    }
}