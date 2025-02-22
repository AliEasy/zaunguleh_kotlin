package top.easyware.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.easyware.core.screens.AppScreens
import top.easyware.core.util.UiText
import top.easyware.event_list.EventListScreen
import top.easyware.settings.SettingsScreen

@Composable
fun HomeScreen(
    appNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val bottomNavigationNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomNavigationNavController = bottomNavigationNavController,
                modifier = Modifier,
                viewModel = viewModel
            )
        }
    )
    {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NavigationGraph(
                bottomNavigationNavController = bottomNavigationNavController,
                appNavController = appNavController,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun BottomBar(
    bottomNavigationNavController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val screens = listOf(
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.EVENTS,
            title = UiText.StringResource(R.string.event).asString(),
            selectedIcon = ImageVector.vectorResource(R.drawable.counter),
            unSelectedIcon = ImageVector.vectorResource(R.drawable.counter),
        ),
    )

    NavigationBar {
        val navBackStackEntry by bottomNavigationNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                modifier = modifier,
                selected = screen.route.value == currentRoute,
                onClick = {
                    bottomNavigationNavController.navigate(screen.route.value) {
                        popUpTo(bottomNavigationNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                    viewModel.onIntent(HomeIntent.SaveLastVisitedTab(screen.route.value))
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.titleSmall,
                    )
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        imageVector = if (screen.route.value == currentRoute) {
                            screen.selectedIcon
                        } else {
                            screen.unSelectedIcon
                        },
                        contentDescription = screen.title
                    )
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(
    bottomNavigationNavController: NavHostController,
    appNavController: NavHostController,
    viewModel: HomeViewModel,
) {
    NavHost(
        navController = bottomNavigationNavController,
        startDestination = viewModel.state.value.lastVisitedTab
            ?: BottomNavigationItemsEnum.EVENTS.value
    ) {
        composable(BottomNavigationItemsEnum.EVENTS.value) {
            EventListScreen(
                navToSubmitPlannerScreen = {
                    appNavController.navigate(AppScreens.SubmitPlanner(plannerId = 1))
                }
            )
        }
        composable(BottomNavigationItemsEnum.SETTINGS.value) {
            SettingsScreen()
        }
    }
}

data class BottomNavigationBarItem(
    val route: BottomNavigationItemsEnum,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

enum class BottomNavigationItemsEnum(val value: String) {
    EVENTS("Events"),
    SETTINGS("Settings"),
}