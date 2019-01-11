package com.dicoding.millatip.footballapps.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun getTimeMillis(dateTime: String) : Long{
    var millisecond = 0L
    val dateFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm", Locale.getDefault())
    try {
        val date = dateFormat.parse(dateTime)
        millisecond = date.time
    }catch (e : Exception){
        e.printStackTrace()
    }

    return millisecond
}

fun dateFormatter(inputDate: String?): String{
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val beforeFormat: Date
    var afterFormat = ""

    try {
        beforeFormat = inputFormat.parse(inputDate)
        afterFormat = outputFormat.format(beforeFormat)
    }catch (e : Exception){
        e.printStackTrace()
    }

    return  afterFormat
}

fun timeFormatter(inputTime: String?) : String{
    val time = inputTime?.split(":")
    return "${time?.get(0)}:${time?.get(1)}"
}

fun toGmtFormat(dateTime: String) : String{
    return try {
        val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val gmtTime = formatter.parse(dateTime)
        formatter.timeZone = TimeZone.getDefault()

        formatter.format(gmtTime)
    }catch (e: Exception){
        "Date Invalid / Not Found"
    }
}