@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidtechchallenge.ui.screens.list

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.ui.navigation.AppScreens
import com.example.androidtechchallenge.ui.theme.AccentColor
import com.example.androidtechchallenge.ui.theme.DarkGrey
import com.example.androidtechchallenge.ui.theme.LightGrey
import com.example.androidtechchallenge.ui.theme.PrimaryLightGrey
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidtechchallenge.domain.models.ChallengeItem
import com.example.androidtechchallenge.ui.components.ErrorScreen
import com.example.androidtechchallenge.ui.components.LoadingScreen

@Composable
fun ChallengesListScreen(
    navController: NavController,
    viewModel: ChallengesListViewModel = viewModel()
) {
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getCompletedChallengesList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Completed Challenges",
                        color = AccentColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.refreshCompletedChallengesList()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = AccentColor
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkGrey)
            )
        }
    ) {
        when (uiState) {
            is ListUiState.Success -> {
                ChallengesListBody(
                    navController = navController,
                    listState = listState,
                    scrollState = scrollState,
                    paddingValues = it,
                    itemList = (uiState as ListUiState.Success).challenges.data
                )
            }

            is ListUiState.Loading -> {
                LoadingScreen()
            }

            is ListUiState.Failed -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun ChallengesListBody(
    navController: NavController,
    listState: LazyListState,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    itemList: List<ChallengeItem>
) {
    Box(
        modifier = Modifier.padding(paddingValues),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(12.dp)
                .scrollable(scrollState, Orientation.Vertical),
            state = listState
        ) {
            items(itemList) {
                ChallengeUIItem(
                    itemId = it.id,
                    name = it.name,
                    completedAt = it.completedAt,
                    completedLanguages = it.completedLanguages.joinToString(),
                    onItemClicked = { id ->
                        navController.navigate(AppScreens.ChallengeDetailScreen.route + "/$id")
                    }
                )
            }
        }
    }
}

@Composable
fun ChallengeUIItem(
    itemId: String,
    name: String,
    completedAt: String,
    completedLanguages: String,
    onItemClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LightGrey, shape = RoundedCornerShape(20.dp))
            .clickable(enabled = true) {
                onItemClicked(itemId)
            }
            .testTag("ListItemContainer")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = name,
                color = PrimaryLightGrey,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = completedAt,
                color = PrimaryLightGrey,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(text = completedLanguages, color = PrimaryLightGrey)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun ChallengesListScreenPreview() {
    ChallengesListScreen(navController = rememberNavController())
}

