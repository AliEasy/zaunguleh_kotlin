package top.easyware.zanguleh.features.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import top.easyware.zanguleh.CalendarScreen
import top.easyware.zanguleh.R
import top.easyware.zanguleh.SettingsScreen
import top.easyware.zanguleh.TasksScreen
import top.easyware.zanguleh.features.daily_counter.presentation.DailyCounterScreen

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val bottomNavigationNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomNavigationNavController = bottomNavigationNavController,
                modifier = Modifier,
            )
        }
    )
    {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NavigationGraph(navController = navController, bottomNavigationNavController = bottomNavigationNavController)
        }
    }
}

@Composable
fun BottomBar(
    bottomNavigationNavController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val screens = listOf(
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.DAILY_COUNTER,
            title = context.getString(R.string.daily_counter),
            selectedIcon = Icons.Filled.Add,
            unSelectedIcon = Icons.Outlined.Add,
        ),
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.TASKS,
            title = context.getString(R.string.tasks),
            selectedIcon = Icons.Filled.Create,
            unSelectedIcon = Icons.Outlined.Create,
        ),
        BottomNavigationBarItem(
            route = BottomNavigationItemsEnum.CALENDAR,
            title = context.getString(R.string.calendar),
            selectedIcon = Icons.Filled.Build,
            unSelectedIcon = Icons.Outlined.Build,
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
                },
                label = {
                    Text(text = screen.title)
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
) {
    NavHost(
        navController = bottomNavigationNavController,
        startDestination = BottomNavigationItemsEnum.DAILY_COUNTER.value
    ) {
        composable(BottomNavigationItemsEnum.DAILY_COUNTER.value) {
            DailyCounterScreen(navController = navController)
        }
        composable(BottomNavigationItemsEnum.TASKS.value) {
            TasksScreen()
        }
        composable(BottomNavigationItemsEnum.CALENDAR.value) {
            CalendarScreen()
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
    TASKS("Tasks"),
    CALENDAR("Calendar"),
    SETTINGS("Settings"),
}