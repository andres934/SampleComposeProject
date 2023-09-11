@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)

package com.example.androidtechchallenge

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.data.mocks.FakeRepositoryImpl
import com.example.androidtechchallenge.data.mocks.challengeDetailMockItem
import com.example.androidtechchallenge.domain.ChallengesUseCase
import com.example.androidtechchallenge.domain.toChallengeDetails
import com.example.androidtechchallenge.ui.screens.details.ChallengeDetailScreen
import com.example.androidtechchallenge.ui.screens.details.ChallengeDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule
import org.junit.Test

class ChallengeDetailsScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val testDispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeRepositoryImpl()
    private val fakeChallengeId = "FakeId"
    private val useCase = ChallengesUseCase(fakeRepository)

    @Test
    fun challengesDetailsScreenShouldShowItemInformationWhenCallSucceeds() {
        val expectedMappedChallenge = challengeDetailMockItem.toChallengeDetails()
        val viewModel = ChallengeDetailViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengeDetailScreen(
                navController = rememberNavController(),
                viewModel = viewModel,
                challengeId = fakeChallengeId
            )
        }

        rule.onNodeWithContentDescription("Go back")
            .assertIsDisplayed()
            .assertHasClickAction()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertIsDisplayed()

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertDoesNotExist()

        // Checks Header Info
        expectedMappedChallenge.rank?.run {
            rule.onNodeWithText(name).assertIsDisplayed()
        }
        rule.onNodeWithText(expectedMappedChallenge.name).assertIsDisplayed()

        rule.onNodeWithContentDescription("Total Stars").assertIsDisplayed()
        rule.onAllNodesWithText("${expectedMappedChallenge.totalStars}")[0].assertIsDisplayed()

        rule.onNodeWithContentDescription("Total Completed").assertIsDisplayed()
        rule.onAllNodesWithText("${expectedMappedChallenge.totalCompleted}")[0].assertIsDisplayed()

        rule.onNodeWithContentDescription("Created By").assertIsDisplayed()
        rule.onAllNodesWithText(expectedMappedChallenge.createdBy.username)[0].assertIsDisplayed()


        // Checks Body Info
        rule.onNodeWithText(expectedMappedChallenge.description).assertIsDisplayed()
        expectedMappedChallenge.tags.forEach {
            rule.onNodeWithText(it).assertIsDisplayed()
        }

        // Checks Stats Info
        rule.onNodeWithText("${expectedMappedChallenge.totalAttempts}").assertIsDisplayed()
        rule.onAllNodesWithText("${expectedMappedChallenge.totalCompleted}")[1].assertIsDisplayed()
        rule.onAllNodesWithText("${expectedMappedChallenge.totalStars}")[1].assertIsDisplayed()
        rule.onNodeWithText("${expectedMappedChallenge.voteScore}").assertIsDisplayed()
        rule.onAllNodesWithText(expectedMappedChallenge.createdBy.username)[1].assertIsDisplayed()
        rule.onNodeWithText(expectedMappedChallenge.publishedAt).assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorMessageIfCallFails() {
        val fakeFailRepository = FakeRepositoryImpl(shouldFail = true)
        val viewModel = ChallengeDetailViewModel(
            useCase = ChallengesUseCase(fakeFailRepository),
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengeDetailScreen(
                navController = rememberNavController(),
                viewModel = viewModel,
                challengeId = fakeChallengeId
            )
        }

        rule.onNodeWithContentDescription("Go back")
            .assertIsDisplayed()
            .assertHasClickAction()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertIsDisplayed()

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onNodeWithTag("ErrorIndicatorContainer")
            .assertIsDisplayed()
        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertDoesNotExist()
    }
}