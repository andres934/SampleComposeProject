package com.example.androidtechchallenge.data

import com.example.androidtechchallenge.data.model.ChallengeResponse
import com.example.androidtechchallenge.data.model.CompletedChallengesResponse

interface ChallengesRepository {

    suspend fun getCompletedChallenges(page: Int): CompletedChallengesResponse?
    suspend fun getChallengeDetails(idChallenge: String): ChallengeResponse?
}