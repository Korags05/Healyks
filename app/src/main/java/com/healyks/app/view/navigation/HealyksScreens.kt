package com.healyks.app.view.navigation

sealed class HealyksScreens(val route: String) {
    object OnBoardingScreen : HealyksScreens("onBoarding_Screen")
    object PostUserBodyScreen : HealyksScreens("postUserBody_Screen")
    object DashboardScreen : HealyksScreens("dashboard_Screen")
}