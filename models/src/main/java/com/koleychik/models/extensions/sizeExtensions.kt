package com.koleychik.models.extensions

import android.content.Context
import com.koleychik.models.R
import java.math.BigDecimal

fun Context.getSizeAbbreviation(value: Long): String {
    val kb = 1024
    val mb = kb * 1024
    val gb = mb * 1024
    return when {
        value < kb -> "$value ${getString(R.string.bytes)}"
        value <= mb -> "${transform(value, kb)} ${getString(R.string.kb)}"
        value <= gb -> "${transform(value, mb)} ${getString(R.string.mb)}"
        else -> "${transform(value, gb)} ${getString(R.string.gb)}"
    }
}

private fun transform(value: Long, size: Int) =
    BigDecimal(value).divide(BigDecimal(size), BigDecimal.ROUND_HALF_EVEN)