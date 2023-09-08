package com.example.androidtechchallenge.domain.models

data class ChallengeDetails(
    val id: String,
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val rank: Rank?,
    val createdBy: UserInfo,
    val approvedBy: UserInfo,
    val totalAttempts: Long,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
    val publishedAt: String,
    val approvedAt: String
)
