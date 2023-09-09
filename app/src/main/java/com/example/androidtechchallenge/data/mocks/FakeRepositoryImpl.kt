package com.example.androidtechchallenge.data.mocks

import com.example.androidtechchallenge.data.ChallengesRepository
import com.example.androidtechchallenge.data.model.ChallengeResponse
import com.example.androidtechchallenge.data.model.CompletedChallengesResponse

class FakeRepositoryImpl(
    private val shouldFail: Boolean = false
) : ChallengesRepository {

    override suspend fun getCompletedChallenges(page: Int): CompletedChallengesResponse {
        if (shouldFail)
            throw Throwable("this is an error")
        return completedChallengesItemMock
    }


    override suspend fun getChallengeDetails(idChallenge: String): ChallengeResponse {
        if (shouldFail)
            throw Throwable("this is an error")
        return challengeDetailMockItem
    }
}