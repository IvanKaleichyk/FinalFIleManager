package com.koleychik.feature_image_info

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestExtensions {

    @Test
    fun testTransformDateFormat(){
        val date = 1577586396000L
        val dateString = transformDateToDateFormat(date)
        assertThat(dateString).contains("2019–12–29")
    }

}