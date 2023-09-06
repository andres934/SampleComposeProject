package com.example.androidtechchallenge.model

data class CompletedChallengesResponse(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<CompletedChallenge>
)
