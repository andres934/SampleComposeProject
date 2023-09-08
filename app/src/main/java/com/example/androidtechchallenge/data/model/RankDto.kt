package com.example.androidtechchallenge.data.model

import com.squareup.moshi.Json

data class RankDto(
    @Json(name = "id")  val id: Long = -1,
    @Json(name = "name")  val name: String = "",
    @Json(name = "color")  val color: String = ""
)
