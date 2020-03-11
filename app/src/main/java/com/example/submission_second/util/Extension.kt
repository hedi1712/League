package com.example.submission_second.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String?.toddMMyyyy(): String? {
    if (!this.isNullOrEmpty()) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        try {
            val date = dateFormat.parse(this)
            return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }
    return null
}
