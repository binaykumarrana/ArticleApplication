package com.test.article.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(timestamp)
}