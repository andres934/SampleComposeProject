package com.example.androidtechchallenge.data.model

import com.squareup.moshi.Json

data class CompletedChallengesResponse(
    @Json(name = "totalPages")  val totalPages: Int = -1,
    @Json(name = "totalItems")  val totalItems: Int = -1,
    @Json(name = "data")  val data: List<CompletedChallengeDto> = emptyList()
)
