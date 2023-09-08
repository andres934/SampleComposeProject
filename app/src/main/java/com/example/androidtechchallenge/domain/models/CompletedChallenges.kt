package com.example.androidtechchallenge.domain.models

data class CompletedChallenges(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<ChallengeItem>
)
