package com.healyks.app.view.navigation

sealed class HealyksScreens(val route: String) {
    object OnBoardingScreen : HealyksScreens("onBoarding_screen")
    object PostUserBodyScreen : HealyksScreens("postUserBody_screen")
    object DashboardScreen : HealyksScreens("dashboard_screen")
    object FirstAidListScreen : HealyksScreens("firstAidList_screen")
    object FirstAidDetailScreen : HealyksScreens("firstAidDetail_screen")
    object ProfileScreen : HealyksScreens("profile_screen")
    object PeriodTrackerScreen : HealyksScreens("periodTracker_screen")
    object NextPeriodCalculatorScreen : HealyksScreens("nextPeriodCalculator_screen")
    object AnalyzeScreen : HealyksScreens("analyze_screen")
    object ReminderScreen : HealyksScreens("reminder_screen")
}