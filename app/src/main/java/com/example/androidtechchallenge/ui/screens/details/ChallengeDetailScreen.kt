@file:OptIn(ExperimentalLayoutApi::class)

package com.example.androidtechchallenge.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.R
import com.example.androidtechchallenge.ui.theme.AccentColor
import com.example.androidtechchallenge.ui.theme.DarkGrey
import com.example.androidtechchallenge.ui.theme.LightGrey
import com.example.androidtechchallenge.ui.theme.PrimaryLightGrey
import com.example.androidtechchallenge.util.getRankColorFromString
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidtechchallenge.domain.models.Rank
import com.example.androidtechchallenge.domain.models.UserInfo
import com.example.androidtechchallenge.ui.components.ErrorScreen
import com.example.androidtechchallenge.ui.components.LoadingScreen

@ExperimentalMaterial3Api
@Composable
fun ChallengeDetailScreen(
    navController: NavController,
    viewModel: ChallengeDetailViewModel = viewModel(),
    challengeId: String?
) {
    val context = LocalView.current.context
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    viewModel.getChallengeDetailsById(challengeId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                            tint = AccentColor,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Challenge Details",
                            color = AccentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkGrey)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding() + 8.dp,
                    bottom = it.calculateBottomPadding() + 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxSize()
                .verticalScroll(scrollState, true),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (uiState) {
                is DetailUIState.Success -> {
                    ChallengeDetailBody(
                        name = (uiState as DetailUIState.Success).challenge.name,
                        description = (uiState as DetailUIState.Success).challenge.description,
                        tags = (uiState as DetailUIState.Success).challenge.tags,
                        rank = (uiState as DetailUIState.Success).challenge.rank,
                        createdBy = (uiState as DetailUIState.Success).challenge.createdBy,
                        totalAttempts = (uiState as DetailUIState.Success).challenge.totalAttempts,
                        totalCompleted = (uiState as DetailUIState.Success).challenge.totalCompleted,
                        totalStars = (uiState as DetailUIState.Success).challenge.totalStars,
                        voteScore = (uiState as DetailUIState.Success).challenge.voteScore,
                        publishedAt = (uiState as DetailUIState.Success).challenge.publishedAt,
                        onLinkClicked = { link ->
                            val urlIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(link)
                            )
                            startActivity(context, urlIntent, null)
                        }
                    )
                }

                is DetailUIState.Loading -> LoadingScreen()
                is DetailUIState.Failed -> ErrorScreen()
            }
        }

    }
}

@Composable
fun ChallengeDetailBody(
    name: String,
    description: String,
    tags: List<String>,
    rank: Rank?,
    createdBy: UserInfo,
    totalAttempts: Long,
    totalCompleted: Long,
    totalStars: Long,
    voteScore: Long,
    publishedAt: String,
    onLinkClicked: (String) -> Unit
) {
    ChallengeDetailHeader(
        rank = rank,
        name = name,
        totalStars = totalStars,
        totalCompleted = totalCompleted,
        creatorUsername = createdBy.username
    )

    ChallengeDetailDescription(
        itemDescription = description,
        tags = tags
    )

    ChallengeDetailStats(
        totalAttempts = totalAttempts,
        totalCompleted = totalCompleted,
        totalStars = totalStars,
        voteScore = voteScore,
        creatorUsername = createdBy.username,
        creatorUrl = createdBy.url,
        publishedAt = publishedAt,
        onLinkClicked = onLinkClicked
    )
}

@Composable
fun ChallengeDetailHeader(
    rank: Rank?,
    name: String,
    totalStars: Long,
    totalCompleted: Long,
    creatorUsername: String
) {
    Box(
        modifier = Modifier
            .background(LightGrey, shape = RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                rank?.run {
                    val rankColor = getRankColorFromString()
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(DarkGrey, CutCornerShape(10.dp))
                            .border(4.dp, rankColor, CutCornerShape(10.dp))
                            .padding(8.dp),
                        text = this.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = rankColor
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 12.dp),
                    text = name,
                    color = PrimaryLightGrey,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Total Stars",
                    tint = PrimaryLightGrey
                )
                Text(
                    text = "$totalStars",
                    color = PrimaryLightGrey
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Total Completed",
                    tint = PrimaryLightGrey
                )
                Text(
                    text = "$totalCompleted",
                    color = PrimaryLightGrey
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Created By",
                    tint = PrimaryLightGrey
                )
                Text(
                    text = creatorUsername,
                    color = PrimaryLightGrey
                )
            }
        }
    }
}

@Composable
fun ChallengeDetailDescription(itemDescription: String, tags: List<String>) {
    Box(
        modifier = Modifier
            .background(LightGrey, shape = RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Description",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryLightGrey
            )
            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = itemDescription,
                fontSize = 16.sp,
                color = PrimaryLightGrey
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tag),
                    contentDescription = "Tags",
                    tint = PrimaryLightGrey
                )
                tags.forEach { tag ->
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .background(DarkGrey)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        text = tag,
                        fontSize = 12.sp,
                        color = PrimaryLightGrey,
                    )
                }
            }
        }
    }
}

@Composable
fun ChallengeDetailStats(
    totalAttempts: Long,
    totalCompleted: Long,
    totalStars: Long,
    voteScore: Long,
    creatorUsername: String,
    creatorUrl: String,
    publishedAt: String,
    onLinkClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(LightGrey, shape = RoundedCornerShape(8.dp))
            .padding(6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Stats",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PrimaryLightGrey
            )
            DetailStatsItem(title = "Total Attempts", value = "$totalAttempts")
            DetailStatsItem(title = "Total completed", value = "$totalCompleted")
            DetailStatsItem(title = "Total stars", value = "$totalStars")
            DetailStatsItem(title = "Vote score", value = "$voteScore")
            DetailStatsItem(title = "Created by", value = creatorUsername) {
                if (creatorUrl.isNotEmpty()) {
                    onLinkClicked(creatorUrl)
                }
            }
            DetailStatsItem(title = "Published at", value = publishedAt)
        }
    }
}

@Composable
fun DetailStatsItem(title: String, value: String, onTextValueClicked: () -> Unit = {}) {
    Divider(modifier = Modifier.height(1.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = title,
            color = PrimaryLightGrey,
            modifier = Modifier
                .weight(2f)
        )
        Text(
            text = value,
            color = PrimaryLightGrey,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onTextValueClicked()
                }
        )
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun ChallengeDetailScreenPreview() {
    ChallengeDetailScreen(
        navController = rememberNavController(),
        challengeId = null
    )
}
