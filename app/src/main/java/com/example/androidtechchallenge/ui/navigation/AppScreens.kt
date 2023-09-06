package com.example.androidtechchallenge.ui.navigation

sealed class AppScreens(val route: String) {
    object ChallengesListScreen: AppScreens("challenges_list_screen")
    object ChallengeDetailScreen: AppScreens("challenge_detail_screen")
}