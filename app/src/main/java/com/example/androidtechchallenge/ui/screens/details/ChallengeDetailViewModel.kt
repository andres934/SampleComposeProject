package com.example.androidtechchallenge.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtechchallenge.data.ChallengesRepository
import com.example.androidtechchallenge.domain.toChallengeDetails
import com.example.androidtechchallenge.ui.screens.list.ListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ChallengeDetailViewModel(
    private val repository: ChallengesRepository = ChallengesRepository(),
    private val backgroundCoroutineContext: CoroutineContext = Dispatchers.IO,
    private val foregroundCoroutineContext: CoroutineContext = Dispatchers.Main
): ViewModel() {

    private val _uiState: MutableStateFlow<DetailUIState> = MutableStateFlow(DetailUIState.Loading)
    val uiState: StateFlow<DetailUIState> = _uiState

    fun getChallengeDetailsById(id: String) {
        viewModelScope.launch(foregroundCoroutineContext) {
            runCatching {
                withContext(backgroundCoroutineContext) {
                    repository.getChallengeDetails(id)
                }
            }.fold(
                {
                    val result = it?.run {
                        DetailUIState.Success(toChallengeDetails())
                    } ?: DetailUIState.Failed
                    _uiState.emit(result)
                },
                {
                    _uiState.emit(DetailUIState.Failed)
                }
            )
        }
    }

    fun setErrorState() {
        viewModelScope.launch {
            _uiState.emit(DetailUIState.Failed)
        }
    }
}
