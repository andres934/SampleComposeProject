package com.example.androidtechchallenge.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtechchallenge.data.ChallengesRepository
import com.example.androidtechchallenge.domain.toCompletedChallenges
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChallengesListViewModel(
    private val repository: ChallengesRepository = ChallengesRepository(),
    private val backgroundCoroutineContext: CoroutineContext = Dispatchers.IO,
    private val foregroundCoroutineContext: CoroutineContext = Dispatchers.Main,
): ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    init {
        getCompletedChallengesList()
    }

    private fun getCompletedChallengesList() {
        viewModelScope.launch(foregroundCoroutineContext) {
            runCatching {
                withContext(backgroundCoroutineContext) {
                    repository.getCompletedChallenges()
                }
            }.fold(
                {
                    val result = it?.run {
                        ListUiState.Success(toCompletedChallenges())
                    } ?: ListUiState.Failed

                    _uiState.emit(result)
                },
                {
                    _uiState.emit(ListUiState.Failed)
                }
            )
        }
    }

    fun refreshCompletedChallengesList() {
        viewModelScope.launch(foregroundCoroutineContext) {
            runCatching {
                _uiState.emit(ListUiState.Loading)
                withContext(backgroundCoroutineContext) {
                    repository.getCompletedChallenges()
                }
            }.fold(
                {
                    val result = it?.run {
                        ListUiState.Success(toCompletedChallenges())
                    } ?: ListUiState.Failed

                    _uiState.emit(result)
                },
                {
                    _uiState.emit(ListUiState.Failed)
                }
            )
        }
    }
}
