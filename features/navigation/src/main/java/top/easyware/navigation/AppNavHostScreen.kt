package top.easyware.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import top.easyware.core.screens.AppScreens
import top.easyware.home.HomeScreen
import top.easyware.intro_slider.IntroSliderScreen
import top.easyware.submit_planner.SubmitPlannerScreen

@Composable
fun AppNavHostScreen(
    navController: NavHostController,
    viewModel: AppNavHostViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = if (viewModel.state.value.introSliderShowed) {
            AppScreens.Home
        } else {
            AppScreens.IntroSlider
        },
    ) {
        composable<AppScreens.Home> {
            HomeScreen(navController)
        }
        composable<AppScreens.SubmitPlanner> { entry ->
            val arg: AppScreens.SubmitPlanner = entry.toRoute()
            SubmitPlannerScreen(
                plannerId = arg.plannerId,
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable<AppScreens.IntroSlider> {
            IntroSliderScreen(
                navToHomeScreen = {
                    navController.navigate(AppScreens.Home) {
                        popUpTo(AppScreens.IntroSlider) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}