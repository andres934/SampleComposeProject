package com.example.androidtechchallenge.ui.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidtechchallenge.ui.theme.AccentColor
import com.example.androidtechchallenge.ui.theme.DarkGreyDefaultAccent

@ExperimentalMaterial3Api
@Composable
fun ChallengeDetailScreen(navController: NavController, challengeId: String?) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Challenge Details", color = DarkGreyDefaultAccent)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AccentColor)
            )
        }
    ) {
        Text(
            text = "This is the future detail challenges screen, be patient",
            modifier = Modifier.padding(it)
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