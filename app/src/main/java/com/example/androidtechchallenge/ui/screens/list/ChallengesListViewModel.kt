package com.example.androidtechchallenge.ui.screens.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtechchallenge.data.ChallengesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChallengesListViewModel(
    private val repository: ChallengesRepository = ChallengesRepository(),
    private val backgroundCoroutineContext: CoroutineContext = Dispatchers.IO
): ViewModel() {

    private val _uiState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _uiState

    init {
        getCompletedChallengesList()
    }

    private fun getCompletedChallengesList() {
        viewModelScope.launch {
            runCatching {
                withContext(backgroundCoroutineContext) {
                    delay(2000)
                    repository.getCompletedChallenges()
                }
            }.fold(
                {
                    val result = it?.run {
                        ListUiState.Success(this)
                    } ?: ListUiState.Failed

                    _uiState.emit(result)
                },
                { error ->
                    Log.e(
                        "CompletedChallengesError",
                        error.message ?: "Failed to get Completed Challenges data"
                    )
                    _uiState.emit(ListUiState.Failed)
                }
            )
        }
    }

    fun refreshCompletedChallengesList() {
        viewModelScope.launch {
            runCatching {
                _uiState.emit(ListUiState.Loading)
                delay(2000)
                withContext(backgroundCoroutineContext) {
                    repository.getCompletedChallenges()
                }
            }.fold(
                {
                    val result = it?.run {
                        ListUiState.Success(this)
                    } ?: ListUiState.Failed

                    _uiState.emit(result)
                },
                { error ->
                    Log.e(
                        "CompletedChallengesError",
                        error.message ?: "Failed to refresh Completed Challenges data"
                    )
                    _uiState.emit(ListUiState.Failed)
                }
            )
        }
    }

}