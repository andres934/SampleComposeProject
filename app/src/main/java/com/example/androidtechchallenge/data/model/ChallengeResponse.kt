package com.example.androidtechchallenge.data.model

import com.squareup.moshi.Json

data class ChallengeResponse(
    @Json(name = "id")  val id: String = "",
    @Json(name = "name")  val name: String = "",
    @Json(name = "slug")  val slug: String = "",
    @Json(name = "url")  val url: String = "",
    @Json(name = "category")  val category: String = "",
    @Json(name = "description")  val description: String = "",
    @Json(name = "tags")  val tags: List<String> = emptyList(),
    @Json(name = "languages")  val languages: List<String> = emptyList(),
    @Json(name = "rank")  val rank: RankDto?,
    @Json(name = "createdBy")  val createdBy: UserInfoDto?,
    @Json(name = "approvedBy")  val approvedBy: UserInfoDto?,
    @Json(name = "totalAttempts")  val totalAttempts: Long = -1,
    @Json(name = "totalCompleted")  val totalCompleted: Long = -1,
    @Json(name = "totalStars")  val totalStars: Long = -1,
    @Json(name = "voteScore")  val voteScore: Long = -1,
    @Json(name = "publishedAt")  val publishedAt: String?,
    @Json(name = "approvedAt")  val approvedAt: String?
)
