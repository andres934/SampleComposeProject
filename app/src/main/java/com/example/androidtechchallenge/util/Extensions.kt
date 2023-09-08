package com.example.androidtechchallenge.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.androidtechchallenge.domain.models.Rank
import com.example.androidtechchallenge.ui.theme.PrimaryLightGrey
import com.example.androidtechchallenge.ui.theme.Purple40
import java.text.SimpleDateFormat
import java.util.Locale


fun Rank.getRankColorFromString() =
    when (color) {
        "white" -> Color.White
        "yellow" -> Color.Yellow
        "blue" -> Color.Blue
        "purple" -> Purple40
        "black" -> Color.Black
        "red" -> Color.Red
        else -> PrimaryLightGrey
    }

fun String.getFormattedDateString(): String =
    runCatching {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

        inputFormat.parse(this)?.run {
            outputFormat.format(this)
        } ?: this@getFormattedDateString
    }.getOrElse {
        this
    }
