package com.healyks.app.view.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.healyks.app.view.screens.DashboardScreen
import com.healyks.app.view.screens.FirstAidDetailScreen
import com.healyks.app.view.screens.FirstAidListScreen
import com.healyks.app.view.screens.NextPeriodCalculatorScreen
import com.healyks.app.view.screens.OnBoardingScreen
import com.healyks.app.view.screens.PeriodTrackerScreen
import com.healyks.app.view.screens.PostUserBodyScreen
import com.healyks.app.view.screens.ProfileScreen

@Composable
fun HealyksNavigation(
    navController: NavHostController = rememberNavController()
) {

    val auth = FirebaseAuth.getInstance()

    var startDestination =
        HealyksScreens.OnBoardingScreen.route

    //temp sol
    val isUserLoggedIn = Firebase.auth.currentUser != null
    if (isUserLoggedIn) {
        startDestination = HealyksScreens.DashboardScreen.route
    }

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
        composable(HealyksScreens.FirstAidListScreen.route) {
            FirstAidListScreen(navController = navController)
        }
        composable(HealyksScreens.FirstAidDetailScreen.route) {
            FirstAidDetailScreen(navController = navController)
        }
        composable(HealyksScreens.PeriodTrackerScreen.route) {
            PeriodTrackerScreen(navController = navController)
        }
        composable(HealyksScreens.NextPeriodCalculatorScreen.route) {
            NextPeriodCalculatorScreen(navController = navController)
        }
        composable(HealyksScreens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                onLogOutClick = {
                    try {
                        auth.signOut()
                        // This is the key fix: clear the entire back stack and navigate to OnBoarding
                        navController.navigate(HealyksScreens.OnBoardingScreen.route) {
                            // Clear the entire back stack
                            popUpTo(0) { inclusive = true }
                        }
                    } catch (e: Exception) {
                        Log.e("Logout", "Logout failed: ${e.message}")
                    }
                }
            )
        }
    }
}