package com.example.androidtechchallenge.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.androidtechchallenge.domain.ChallengesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChallengesListViewModel(
    private val useCase: ChallengesUseCase = ChallengesUseCase(),
    private val backgroundCoroutineContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    fun getCompletedChallengesList() {
        viewModelScope.launch(backgroundCoroutineContext) {
            runCatching {
                useCase.getCompletedChallengesList(viewModelScope)
            }.fold(
                {
                    _uiState.emit(ListUiState.Success(it))
                },
                {
                    _uiState.emit(ListUiState.Failed)
                }
            )
        }
    }

    fun refreshCompletedChallengesList() {
        viewModelScope.launch(backgroundCoroutineContext) {
            _uiState.emit(ListUiState.Loading)
        }
        getCompletedChallengesList()
    }
}
