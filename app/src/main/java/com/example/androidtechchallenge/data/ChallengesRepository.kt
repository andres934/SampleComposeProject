package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.data.model.ChallengeResponse
import com.example.androidtechchallenge.data.model.CompletedChallengesResponse

interface ChallengesRepository {

    suspend fun getCompletedChallenges(page: Int = 0): CompletedChallengesResponse?
    suspend fun getChallengeDetails(idChallenge: String): ChallengeResponse?
}