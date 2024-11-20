package com.findme.frameworklayer.utility

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"
const val DATE_PATTERN_RESULT = "yyyy-MM-dd HH:mm a"

@SuppressLint("NewApi")
fun String.changeDateFrom(): String {
    try {
        val sdf = SimpleDateFormat(DATE_PATTERN, Locale.US)
        val date = sdf.parse(this)
        val newSdf = SimpleDateFormat(DATE_PATTERN_RESULT, Locale.US)
        val format = newSdf.format(date!!)
        return format
    } catch (e: Exception) {
        println(e.message)
    }
    return this
}