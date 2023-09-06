package com.example.androidtechchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtechchallenge.ui.screens.details.ChallengeDetailScreen
import com.example.androidtechchallenge.ui.screens.list.ChallengesListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.ChallengesListScreen.route
    ) {
        composable(route = AppScreens.ChallengesListScreen.route) {
            ChallengesListScreen(navController)
        }
        composable(
            route = AppScreens.ChallengeDetailScreen.route + "/{idChallenge}",
            arguments = listOf(navArgument(name = "idChallenge") {
                type = NavType.StringType
            })
        ) {
            ChallengeDetailScreen(navController, it.arguments?.getString("idChallenge"))
        }
    }
}