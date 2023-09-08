package com.example.androidtechchallenge.model

import com.squareup.moshi.Json

data class CompletedChallenge(
    @Json(name = "id")  val id: String = "",
    @Json(name = "name")  val name: String = "",
    @Json(name = "slug")  val slug: String = "",
    @Json(name = "completedAt")  val completedAt: String = "",
    @Json(name = "completedLanguages")  val completedLanguages: List<String> = emptyList()
)
