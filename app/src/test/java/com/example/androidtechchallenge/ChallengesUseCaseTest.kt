package com.example.androidtechchallenge

import com.example.androidtechchallenge.data.mocks.FakeRepositoryImpl
import com.example.androidtechchallenge.data.mocks.challengeDetailMockItem
import com.example.androidtechchallenge.data.mocks.completedChallengesItemMock
import com.example.androidtechchallenge.domain.ChallengesUseCase
import com.example.androidtechchallenge.domain.toChallengeDetails
import com.example.androidtechchallenge.domain.toCompletedChallenges
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ChallengesUseCaseTest {

    private val fakeRepository = FakeRepositoryImpl()
    private val useCase = ChallengesUseCase(fakeRepository)

    @Test
    fun `on getCompletedChallengesList success call should return mapped object`() {
        runTest {
            // Given
            val expectedMappedResponse = completedChallengesItemMock.toCompletedChallenges()

            // When
            val response = useCase.getCompletedChallengesList()

            // Then
            assert(response == expectedMappedResponse)
        }
    }

    @Test
    fun `on getChallengeDetailsById success call should return mapped object`() {
        runTest {
            // Given
            val expectedMappedResponse = challengeDetailMockItem.toChallengeDetails()
            val testId = "This is an ID"

            // When
            val response = useCase.getChallengeDetailsById(testId)

            // Then
            assert(response == expectedMappedResponse)
        }
    }
}