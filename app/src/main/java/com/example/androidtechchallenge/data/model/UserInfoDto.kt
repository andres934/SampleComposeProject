package com.example.androidtechchallenge.data.model

import com.squareup.moshi.Json

data class UserInfoDto(
    @Json(name = "username")  val username: String = "",
    @Json(name = "url")  val url: String = ""
)
