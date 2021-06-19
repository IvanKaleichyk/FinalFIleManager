package com.koleychik.models.extensions

import android.content.Context
import com.koleychik.models.R
import java.math.BigDecimal

fun Context.getSizeAbbreviation(value: Long): String {
    val kb = 1024
    val mb = kb * 1024
    val gb = mb * 1024
    return when {
        value <= kb -> "$value ${getString(R.string.bytes)}"
        value <= mb -> "${transform(value, kb)} ${getString(R.string.kb)}"
        value <= gb -> "${transform(value, mb)} ${getString(R.string.mb)}"
        else -> "${transform(value, gb)} ${getString(R.string.gb)}"
    }
}

fun Context.getTime(value: Long): String {
    val sec = 1000
    val min = sec * 60
    val h = min * 60
    return when {
        value <= sec -> "$value ${getString(R.string.milliseconds)}"
        value <= min -> "${transform(value, sec)} ${getString(R.string.seconds)}"
        value <= h -> "${transform(value, min)} ${getString(R.string.minutes)}"
        else -> "${transform(value, h)} ${getString(R.string.hours)}"
    }
}

private fun transform(value: Long, size: Int) =
    BigDecimal(value).divide(BigDecimal(size), BigDecimal.ROUND_HALF_EVEN)