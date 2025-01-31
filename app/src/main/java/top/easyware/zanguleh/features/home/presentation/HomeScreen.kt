package top.easyware.zanguleh.features.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.easyware.zanguleh.R
import top.easyware.zanguleh.SettingsScreen
import top.easyware.zanguleh.features.daily_counter.presentation.DailyCounterScreen

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
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
                navController = navController,
                bottomNavigationNavController = bottomNavigationNavController,
                viewModel = viewModel
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
    val context = LocalContext.current

    val screens = listOf(
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.DAILY_COUNTER,
            title = context.getString(R.string.daily_counter),
            selectedIcon = ImageVector.vectorResource(R.drawable.counter),
            unSelectedIcon = ImageVector.vectorResource(R.drawable.counter),
        ),
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.SETTINGS,
            title = context.getString(R.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
        )
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

                    viewModel.saveLastVisitedTab(screen.route.value)
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
    navController: NavHostController,
    bottomNavigationNavController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = bottomNavigationNavController,
        startDestination = viewModel.getLastVisitedTab()
    ) {
        composable(BottomNavigationItemsEnum.DAILY_COUNTER.value) {
            DailyCounterScreen(navController = navController)
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
    DAILY_COUNTER("DailyCounter"),
    SETTINGS("Settings"),
}