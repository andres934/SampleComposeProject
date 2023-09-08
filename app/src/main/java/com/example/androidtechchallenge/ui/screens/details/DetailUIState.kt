package com.example.androidtechchallenge.ui.screens.details

import com.example.androidtechchallenge.domain.models.ChallengeDetails

sealed class DetailUIState {
    class Success(val challenge: ChallengeDetails): DetailUIState()
    object Loading: DetailUIState()
    object Failed: DetailUIState()
}