@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.androidtechchallenge

import com.example.androidtechchallenge.data.ChallengesRepositoryImpl
import com.example.androidtechchallenge.data.mocks.challengeDetailMockItem
import com.example.androidtechchallenge.domain.ChallengesUseCase
import com.example.androidtechchallenge.domain.toChallengeDetails
import com.example.androidtechchallenge.ui.screens.details.ChallengeDetailViewModel
import com.example.androidtechchallenge.ui.screens.details.DetailUIState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

class ChallengeDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockRepository = mockk<ChallengesRepositoryImpl>()
    private val useCase = ChallengesUseCase(repository = mockRepository)

    @Test
    fun `on viewModel initialized uiState starts with Loading as default`() {
        // Given
        val viewModel = ChallengeDetailViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )

        // Then
        assert(viewModel.uiState.value is DetailUIState.Loading)
    }

    @Test
    fun `on getChallengeDetailsById success call uiState updates to Success`() {
        // Given
        val expectedId = "5277c8a221e209d3f6000b56"
        coEvery {
            mockRepository.getChallengeDetails(expectedId)
        } returns challengeDetailMockItem

        val viewModel = ChallengeDetailViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )

        // When
        viewModel.getChallengeDetailsById(expectedId)

        // Then
        coVerify(exactly = 1) {
            mockRepository.getChallengeDetails(expectedId)
        }
        assert(viewModel.uiState.value is DetailUIState.Success)
        assert((viewModel.uiState.value as DetailUIState.Success).challenge == challengeDetailMockItem.toChallengeDetails())

        confirmVerified(mockRepository)
    }

    @Test
    fun `on getChallengeDetailsById failed call uiState updates to Failed`() {
        // Given
        val expectedId = "5277c8a221e209d3f6000b56"
        val expectedException = Throwable("This is an error")
        coEvery {
            mockRepository.getChallengeDetails(expectedId)
        } throws expectedException

        val viewModel = ChallengeDetailViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )

        // When
        viewModel.getChallengeDetailsById(expectedId)

        // Then
        coVerify(exactly = 1) {
            mockRepository.getChallengeDetails(expectedId)
        }
        assert(viewModel.uiState.value is DetailUIState.Failed)

        confirmVerified(mockRepository)
    }

    @Test
    fun `on challenge id null passed should fail call and uiState updates to Failed`() {
        // Given
        val expectedId: String? = null

        val viewModel = ChallengeDetailViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )

        // When
        viewModel.getChallengeDetailsById(expectedId)

        // Then
        assert(viewModel.uiState.value is DetailUIState.Failed)

        confirmVerified(mockRepository)
    }
}