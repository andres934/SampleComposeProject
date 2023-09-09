@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.androidtechchallenge

import com.example.androidtechchallenge.data.ChallengesRepositoryImpl
import com.example.androidtechchallenge.data.mocks.completedChallengesItemMock
import com.example.androidtechchallenge.domain.toCompletedChallenges
import com.example.androidtechchallenge.ui.screens.list.ChallengesListViewModel
import com.example.androidtechchallenge.ui.screens.list.ListUiState
import io.mockk.coEvery
import io.mockk.coJustAwait
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

class ChallengesListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockRepository = mockk<ChallengesRepositoryImpl>()

    @Test
    fun `on viewModel initialized uiState starts with Loading and calls getCompletedChallenges`() {
        // Given
        coJustAwait {
            mockRepository.getCompletedChallenges()
        }

        // When
        val viewModel = ChallengesListViewModel(
            repository = mockRepository,
            backgroundCoroutineContext = testDispatcher,
            foregroundCoroutineContext = testDispatcher
        )

        // Then
        coVerify(exactly = 1) {
            mockRepository.getCompletedChallenges()
        }

        assert(viewModel.uiState.value is ListUiState.Loading)

        confirmVerified(mockRepository)
    }

    @Test
    fun `on getCompletedChallenges succeed call should update uiState with Success`() {
        // Given
        coEvery {
            mockRepository.getCompletedChallenges()
        } returns completedChallengesItemMock

        // When
        val viewModel = ChallengesListViewModel(
            repository = mockRepository,
            backgroundCoroutineContext = testDispatcher,
            foregroundCoroutineContext = testDispatcher
        )

        // Then
        coVerify(exactly = 1) {
            mockRepository.getCompletedChallenges()
        }

        assert(viewModel.uiState.value is ListUiState.Success)
        assert((viewModel.uiState.value as ListUiState.Success).challenges == completedChallengesItemMock.toCompletedChallenges())

        confirmVerified(mockRepository)
    }

    @Test
    fun `on refreshCompletedChallengesList succeed call should update uiState to Success`() {
        // Given
        coEvery {
            mockRepository.getCompletedChallenges()
        } returns completedChallengesItemMock

        val viewModel = ChallengesListViewModel(
            repository = mockRepository,
            backgroundCoroutineContext = testDispatcher,
            foregroundCoroutineContext = testDispatcher
        )

        // When
        viewModel.refreshCompletedChallengesList()

        // Then
        coVerify(exactly = 2) {
            mockRepository.getCompletedChallenges()
        }

        assert(viewModel.uiState.value is ListUiState.Success)
        assert((viewModel.uiState.value as ListUiState.Success).challenges == completedChallengesItemMock.toCompletedChallenges())

        confirmVerified(mockRepository)
    }

    @Test
    fun `on getCompletedChallenges failure call should update uiState with Failed`() {
        // Given
        val expectedException = Throwable("This is an error")
        coEvery {
            mockRepository.getCompletedChallenges()
        } throws expectedException

        // When
        val viewModel = ChallengesListViewModel(
            repository = mockRepository,
            backgroundCoroutineContext = testDispatcher,
            foregroundCoroutineContext = testDispatcher
        )

        // Then
        coVerify(exactly = 1) {
            mockRepository.getCompletedChallenges()
        }

        assert(viewModel.uiState.value is ListUiState.Failed)

        confirmVerified(mockRepository)
    }

    @Test
    fun `on refreshCompletedChallengesList failed call should update uiState to Failed`() {
        // Given
        val expectedException = Throwable("This is an error")
        coEvery {
            mockRepository.getCompletedChallenges()
        } throws expectedException

        val viewModel = ChallengesListViewModel(
            repository = mockRepository,
            backgroundCoroutineContext = testDispatcher,
            foregroundCoroutineContext = testDispatcher
        )

        // When
        viewModel.refreshCompletedChallengesList()

        // Then
        coVerify(exactly = 2) {
            mockRepository.getCompletedChallenges()
        }

        assert(viewModel.uiState.value is ListUiState.Failed)

        confirmVerified(mockRepository)
    }
}