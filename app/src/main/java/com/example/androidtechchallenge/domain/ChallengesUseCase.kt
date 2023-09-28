package com.example.androidtechchallenge.domain

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.androidtechchallenge.data.ChallengesPagingSource
import com.example.androidtechchallenge.data.ChallengesRepository
import com.example.androidtechchallenge.data.ChallengesRepositoryImpl
import com.example.androidtechchallenge.domain.models.ChallengeDetails
import com.example.androidtechchallenge.domain.models.ChallengeItem
import com.example.androidtechchallenge.domain.models.CompletedChallenges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ChallengesUseCase(
    private val repository: ChallengesRepository = ChallengesRepositoryImpl()
) {
    fun getCompletedChallengesList(viewModelScope: CoroutineScope): Flow<PagingData<ChallengeItem>> =
        GetPagedChallenges(repository)(viewModelScope)


    suspend fun getChallengeDetailsById(challengeId: String): ChallengeDetails? =
        repository.getChallengeDetails(challengeId)?.toChallengeDetails()
}