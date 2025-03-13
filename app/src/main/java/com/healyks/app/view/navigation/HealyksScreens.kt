package com.healyks.app.view.navigation

sealed class HealyksScreens(val route: String) {
    object OnBoardingScreen : HealyksScreens("onBoarding_screen")
    object PostUserBodyScreen : HealyksScreens("postUserBody_screen")
    object DashboardScreen : HealyksScreens("dashboard_screen")
    object FirstAidListScreen : HealyksScreens("firstAidList_screen")
    object FirstAidDetailScreen : HealyksScreens("firstAidDetail_screen")
}