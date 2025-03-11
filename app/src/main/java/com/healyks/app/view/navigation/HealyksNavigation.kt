package com.healyks.app.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.healyks.app.view.screens.DashboardScreen
import com.healyks.app.view.screens.OnBoardingScreen
import com.healyks.app.view.screens.PostUserBodyScreen

@Composable
fun HealyksNavigation(
    navController: NavHostController = rememberNavController()
) {

    val startDestination =
        HealyksScreens.DashboardScreen.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(HealyksScreens.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }

        composable(HealyksScreens.PostUserBodyScreen.route) {
            PostUserBodyScreen(navController = navController)
        }

        composable(HealyksScreens.DashboardScreen.route) {
            DashboardScreen(navController = navController)
        }
    }

}