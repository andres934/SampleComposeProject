package com.example.androidtechchallenge.ui.screens.details

import com.example.androidtechchallenge.data.ChallengesRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ChallengeDetailViewModel(
    repository: ChallengesRepository,
    backgroundCoroutineContext: CoroutineContext = Dispatchers.IO,
    foregroundCoroutineContext: CoroutineContext = Dispatchers.Main
) {

}