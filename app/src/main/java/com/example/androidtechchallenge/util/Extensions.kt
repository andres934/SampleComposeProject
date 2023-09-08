package com.example.androidtechchallenge.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.androidtechchallenge.model.Rank
import com.example.androidtechchallenge.ui.theme.PrimaryLightGrey
import com.example.androidtechchallenge.ui.theme.Purple40
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun Rank.getRankColorFromString() =
    when (color) {
        "yellow" -> Color.Yellow
        "blue" -> Color.Blue
        "purple" -> Purple40
        "darkgray" -> Color.DarkGray
        "grey" -> Color.Gray
        "white" -> Color.White
        else -> PrimaryLightGrey
    }

fun String.getFormattedDateString(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

    return runCatching {
        val parsedDate: Date? = inputFormat.parse(this)
        outputFormat.format(parsedDate)
    }.getOrElse {
        Log.e("DateParsing", it.message ?: "Failed parsing $this date")
        null
    }
}

fun String.stringToDate(): Date? =
    runCatching {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(this)
    }.getOrElse { null }
