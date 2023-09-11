@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.androidtechchallenge

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.data.mocks.FakeRepositoryImpl
import com.example.androidtechchallenge.data.mocks.completedChallengesItemMock
import com.example.androidtechchallenge.domain.ChallengesUseCase
import com.example.androidtechchallenge.ui.screens.list.ChallengesListScreen
import com.example.androidtechchallenge.ui.screens.list.ChallengesListViewModel
import com.example.androidtechchallenge.util.getFormattedDateString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule
import org.junit.Test

class ChallengeListScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val testDispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeRepositoryImpl()

    @Test
    fun challengesListShouldShowItemsWhenCallSucceeds() {
        val useCase = ChallengesUseCase(fakeRepository)
        val viewModel = ChallengesListViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengesListScreen(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }

        rule.onNodeWithContentDescription("Refresh")
            .assertIsDisplayed()
            .assertHasClickAction()

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onAllNodesWithTag("ListItemContainer").assertCountEquals(2)
        rule.onAllNodesWithTag("ListItemContainer")[0].assertIsDisplayed().assertHasClickAction()
        rule.onAllNodesWithTag("ListItemContainer")[1].assertIsDisplayed().assertHasClickAction()

        rule.onNodeWithText(completedChallengesItemMock.data[0].name).assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[0].completedAt.getFormattedDateString())
            .assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[0].completedLanguages.joinToString())
            .assertIsDisplayed()

        rule.onNodeWithText(completedChallengesItemMock.data[1].name).assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[1].completedAt.getFormattedDateString())
            .assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[1].completedLanguages.joinToString())
            .assertIsDisplayed()
    }

    @Test
    fun whenRefreshTriggeredShouldShowProgressIndicatorAndOnSuccessShowItems() {
        val useCase = ChallengesUseCase(fakeRepository)
        val viewModel = ChallengesListViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengesListScreen(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }

        rule.onNodeWithContentDescription("Refresh")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertIsDisplayed()

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onNodeWithText(completedChallengesItemMock.data[0].name).assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[0].completedAt.getFormattedDateString())
            .assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[0].completedLanguages.joinToString())
            .assertIsDisplayed()

        rule.onNodeWithText(completedChallengesItemMock.data[1].name).assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[1].completedAt.getFormattedDateString())
            .assertIsDisplayed()
        rule.onNodeWithText(completedChallengesItemMock.data[1].completedLanguages.joinToString())
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorMessageIfCallFails() {
        val fakeFailRepository = FakeRepositoryImpl(shouldFail = true)
        val useCase = ChallengesUseCase(fakeFailRepository)
        val viewModel = ChallengesListViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengesListScreen(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onNodeWithContentDescription("Refresh")
            .assertIsDisplayed()
            .assertHasClickAction()

        rule.onNodeWithTag("ErrorIndicatorContainer")
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorMessageIfCallFailsWhenRefreshing() {
        val fakeFailRepository = FakeRepositoryImpl(shouldFail = true)
        val useCase = ChallengesUseCase(fakeFailRepository)
        val viewModel = ChallengesListViewModel(
            useCase = useCase,
            backgroundCoroutineContext = testDispatcher
        )
        rule.setContent {
            ChallengesListScreen(
                navController = rememberNavController(),
                viewModel = viewModel
            )
        }

        rule.onNodeWithContentDescription("Refresh")
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertIsDisplayed()
        rule.onNodeWithTag("ErrorIndicatorContainer")
            .assertDoesNotExist()

        testDispatcher.scheduler.advanceUntilIdle()

        rule.onNodeWithTag("ProgressIndicatorContainer")
            .assertDoesNotExist()
        rule.onNodeWithTag("ErrorIndicatorContainer")
            .assertIsDisplayed()
    }
}
