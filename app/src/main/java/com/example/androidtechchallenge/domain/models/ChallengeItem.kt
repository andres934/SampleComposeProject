package com.example.androidtechchallenge.domain.models

data class ChallengeItem(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: List<String>
)
