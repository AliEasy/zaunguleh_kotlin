package top.easyware.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.easyware.core.screens.AppScreens
import top.easyware.home.HomeScreen
import top.easyware.intro_slider.IntroSliderScreen
import top.easyware.submit_planner.SubmitPlannerScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.IntroSliderScreen.route,
    ) {
        composable(
            route = AppScreens.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = AppScreens.SubmitPlannerScreen.route + "/reminderId={reminderId}",
            arguments = listOf(navArgument("reminderId") {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            })
        ) { entry ->
            SubmitPlannerScreen(
                navController = navController,
                reminderId = entry.arguments?.getInt("reminderId") ?: -1
            )
        }
        composable(
            route = AppScreens.IntroSliderScreen.route
        ) {
            IntroSliderScreen()
        }
    }
}