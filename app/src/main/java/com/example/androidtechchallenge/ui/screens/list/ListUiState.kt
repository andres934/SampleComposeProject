package com.example.androidtechchallenge.ui.screens.list

import com.example.androidtechchallenge.model.CompletedChallengesResponse

sealed class ListUiState {
    class Success(val challenges: CompletedChallengesResponse): ListUiState()
    object Loading: ListUiState()
    object Failed: ListUiState()
}