package com.example.androidtechchallenge.ui.screens.list

import com.example.androidtechchallenge.domain.models.CompletedChallenges

sealed class ListUiState {
    class Success(val challenges: CompletedChallenges): ListUiState()
    object Loading: ListUiState()
    object Failed: ListUiState()
}