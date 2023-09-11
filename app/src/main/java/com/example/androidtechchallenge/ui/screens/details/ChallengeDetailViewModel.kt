package com.example.androidtechchallenge.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtechchallenge.domain.ChallengesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChallengeDetailViewModel(
    private val useCase: ChallengesUseCase = ChallengesUseCase(),
    private val backgroundCoroutineContext: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUIState> = MutableStateFlow(DetailUIState.Loading)
    val uiState: StateFlow<DetailUIState> = _uiState

    fun getChallengeDetailsById(id: String?) {
        viewModelScope.launch(backgroundCoroutineContext) {
            when {
                id.isNullOrEmpty() -> {
                    _uiState.emit(DetailUIState.Failed)
                }
                else -> {
                    runCatching {
                        useCase.getChallengeDetailsById(id)
                    }.fold(
                        {
                            val result = it?.run {
                                DetailUIState.Success(this)
                            } ?: DetailUIState.Failed
                            _uiState.emit(result)
                        },
                        {
                            _uiState.emit(DetailUIState.Failed)
                        }
                    )
                }
            }
        }
    }
}
