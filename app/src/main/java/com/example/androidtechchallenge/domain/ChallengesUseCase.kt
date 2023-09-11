package com.example.androidtechchallenge.domain

import com.example.androidtechchallenge.data.ChallengesRepository
import com.example.androidtechchallenge.data.ChallengesRepositoryImpl
import com.example.androidtechchallenge.domain.models.ChallengeDetails
import com.example.androidtechchallenge.domain.models.CompletedChallenges

class ChallengesUseCase(
    private val repository: ChallengesRepository = ChallengesRepositoryImpl()
) {

    suspend fun getCompletedChallengesList(): CompletedChallenges? =
        repository.getCompletedChallenges()?.toCompletedChallenges()

    suspend fun getChallengeDetailsById(challengeId: String): ChallengeDetails? =
        repository.getChallengeDetails(challengeId)?.toChallengeDetails()
}