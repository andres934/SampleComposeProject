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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.data.challengesMockItems
import com.example.androidtechchallenge.model.CompletedChallenge
import com.example.androidtechchallenge.ui.navigation.AppScreens
import com.example.androidtechchallenge.ui.theme.AccentColor
import com.example.androidtechchallenge.ui.theme.DarkGreyDefaultAccent
import com.example.androidtechchallenge.ui.theme.PrimaryTextColor

@ExperimentalMaterial3Api
@Composable
fun ChallengesListScreen(navController: NavController) {
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Challenges List", color = DarkGreyDefaultAccent)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AccentColor)
            )
        }
    ) {
        ChallengesListBody(
            navController = navController,
            listState = listState,
            scrollState = scrollState,
            paddingValues = it
        )
    }
}

@Composable
fun ChallengesListBody(
    navController: NavController,
    listState: LazyListState,
    scrollState: ScrollState,
    paddingValues: PaddingValues
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .scrollable(scrollState, Orientation.Vertical),
            state = listState
        ) {
            items(challengesMockItems) {
                ChallengeItem(
                    item = it,
                    onItemClicked = { id ->
                        navController.navigate(AppScreens.ChallengeDetailScreen.route + "/$id")
                    }
                )
            }
        }
    }
}

@Composable
fun ChallengeItem(item: CompletedChallenge, onItemClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DarkGreyDefaultAccent, shape = RoundedCornerShape(20.dp))
            .clickable(enabled = true) {
                onItemClicked(item.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = item.name,
                color = PrimaryTextColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.completedAt,
                color = PrimaryTextColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = item.completedLanguages.joinToString(), color = PrimaryTextColor)
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

