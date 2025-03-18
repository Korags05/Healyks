package com.healyks.app.view.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.view.screens.*

@Composable
fun HealyksNavigation(
    navController: NavHostController = rememberNavController()
) {
    val auth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser != null) {
        HealyksScreens.DashboardScreen.route
    } else {
        HealyksScreens.OnBoardingScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding to PostUserBody (Already working well)
        composable(
            route = HealyksScreens.OnBoardingScreen.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(500)
                )
            }
        ) {
            OnBoardingScreen(navController = navController)
        }

        composable(
            route = HealyksScreens.PostUserBodyScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, tween(500)
                )
            }
        ) {
            PostUserBodyScreen(navController = navController)
        }

        // Dashboard (No animation needed as it's the main screen)
        composable(
            HealyksScreens.DashboardScreen.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            DashboardScreen(navController = navController)
        }

        // First Aid List
        composable(
            HealyksScreens.FirstAidListScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            FirstAidListScreen(navController = navController)
        }

        // First Aid Detail
        composable(
            HealyksScreens.FirstAidDetailScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            FirstAidDetailScreen(navController = navController)
        }

        // Period Tracker
        composable(
            HealyksScreens.PeriodTrackerScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            PeriodTrackerScreen(navController = navController)
        }

        // Next Period Calculator
        composable(
            HealyksScreens.NextPeriodCalculatorScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            NextPeriodCalculatorScreen(navController = navController)
        }

        // Analyze Screen
        composable(
            HealyksScreens.AnalyzeScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            AnalyzeScreen(navController = navController)
        }

        // Profile Screen (Handles Logout)
        composable(
            HealyksScreens.ProfileScreen.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            ProfileScreen(
                navController = navController,
                onLogOutClick = {
                    try {
                        auth.signOut()
                        // Navigate back to onboarding and clear back stack
                        navController.navigate(HealyksScreens.OnBoardingScreen.route) {
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
