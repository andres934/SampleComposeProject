@file:OptIn(ExperimentalLayoutApi::class)

package com.example.androidtechchallenge.ui.screens.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.androidtechchallenge.data.challengeDetailMockItem
import com.example.androidtechchallenge.model.ChallengeResponse
import com.example.androidtechchallenge.ui.theme.AccentColor
import com.example.androidtechchallenge.ui.theme.DarkGrey
import com.example.androidtechchallenge.ui.theme.LightGrey
import com.example.androidtechchallenge.ui.theme.PrimaryLightGrey
import com.example.androidtechchallenge.util.getFormattedDateString
import com.example.androidtechchallenge.util.getRankColorFromString

@ExperimentalMaterial3Api
@Composable
fun ChallengeDetailScreen(navController: NavController, challengeId: String?) {
    val scrollState = rememberScrollState()
    val context = LocalView.current.context
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
                        Text(text = "Challenge Details", color = AccentColor, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = DarkGrey)
            )
        }
    ) {
        ChallengeDetailBody(
            item = challengeDetailMockItem,
            paddingValues = it,
            scrollState = scrollState,
            onLinkClicked = { link ->
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link)
                )
                startActivity(context, urlIntent, null)
            }
        )
    }
}

@Composable
fun ChallengeDetailBody(
    item: ChallengeResponse,
    paddingValues: PaddingValues,
    scrollState: ScrollState,
    onLinkClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = paddingValues.calculateTopPadding() + 8.dp,
                bottom = paddingValues.calculateBottomPadding() + 8.dp,
                start = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth()
            .verticalScroll(scrollState, true),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        ChallengeDetailHeader(item)

        ChallengeDetailDescription(item)

        ChallengeDetailStats(item = item, onLinkClicked = onLinkClicked)
    }
}

@Composable
fun ChallengeDetailHeader(item: ChallengeResponse) {
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
                val rankColor = item.rank.getRankColorFromString()
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(DarkGrey, CutCornerShape(10.dp))
                        .border(4.dp, rankColor, CutCornerShape(10.dp))
                        .padding(8.dp),
                    text = item.rank.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = rankColor
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 12.dp),
                    text = item.name,
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
                    text = "${item.totalStars}",
                    color = PrimaryLightGrey
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "Total Completed",
                    tint = PrimaryLightGrey
                )
                Text(
                    text = "${item.totalCompleted}",
                    color = PrimaryLightGrey
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Created By",
                    tint = PrimaryLightGrey
                )
                Text(
                    text = item.createdBy.username,
                    color = PrimaryLightGrey
                )
            }
        }
    }
}

@Composable
fun ChallengeDetailDescription(item: ChallengeResponse) {
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
                text = item.description,
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
                item.tags.forEach { tag ->
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
fun ChallengeDetailStats(item: ChallengeResponse, onLinkClicked: (String) -> Unit) {
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
            DetailStatsItem(title = "Total Attempts", value = "${item.totalAttempts}")
            DetailStatsItem(title = "Total completed", value = "${item.totalCompleted}")
            DetailStatsItem(title = "Total stars", value = "${item.totalStars}")
            DetailStatsItem(title = "Vote score", value = "${item.voteScore}")
            DetailStatsItem(title = "Created by", value = item.createdBy.username) {
                onLinkClicked(item.createdBy.url)
            }
            item.publishedAt.getFormattedDateString()?.run {
                DetailStatsItem(title = "Published at", value = this)
            }
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
