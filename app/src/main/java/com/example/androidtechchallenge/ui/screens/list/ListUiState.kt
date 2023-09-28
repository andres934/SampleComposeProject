package com.example.androidtechchallenge.ui.screens.list

import androidx.paging.PagingData
import com.example.androidtechchallenge.domain.models.ChallengeItem
import kotlinx.coroutines.flow.Flow

sealed class ListUiState {
    class Success(val challenges: Flow<PagingData<ChallengeItem>>): ListUiState()
    object Loading: ListUiState()
    object Failed: ListUiState()
}