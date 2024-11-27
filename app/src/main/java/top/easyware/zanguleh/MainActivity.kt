package top.easyware.zanguleh

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import top.easyware.zanguleh.core.screens.Screens
import top.easyware.zanguleh.features.home.presentation.HomeScreen
import top.easyware.zanguleh.features.submit_reminder.presentation.SubmitReminderScreen
import top.easyware.zanguleh.ui.theme.ZangulehTheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        val locale = Locale("fa")
        Locale.setDefault(locale)

        val config = newBase.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        val context = newBase.createConfigurationContext(config)

        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ZangulehTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screens.HomeScreen.route,
                    ) {
                        composable(
                            route = Screens.HomeScreen.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screens.SubmitReminderScreen.route + "/reminderId={reminderId}",
                            arguments = listOf(navArgument("reminderId") {
                                type = NavType.IntType
                                defaultValue = -1
                                nullable = false
                            })
                        ) { entry ->
                            SubmitReminderScreen(
                                navController = navController,
                                reminderId = entry.arguments?.getInt("reminderId") ?: -1
                            )
                        }
                    }
                }
            }
        }
    }
}
//
//@Composable
//fun BottomBar(
//    navController: NavHostController,
//    context: Context,
//    modifier: Modifier = Modifier,
//) {
//    val screens = listOf(
//        BottomNavigationBarItem(
//            route = BottomNavigationItemsEnum.DAILY_COUNTER,
//            title = context.getString(R.string.daily_counter),
//            selectedIcon = Icons.Filled.Add,
//            unSelectedIcon = Icons.Outlined.Add,
//        ),
//        BottomNavigationBarItem(
//            route = BottomNavigationItemsEnum.TASKS,
//            title = context.getString(R.string.tasks),
//            selectedIcon = Icons.Filled.Create,
//            unSelectedIcon = Icons.Outlined.Create,
//        ),
//        BottomNavigationBarItem(
//            route = BottomNavigationItemsEnum.CALENDAR,
//            title = context.getString(R.string.calendar),
//            selectedIcon = Icons.Filled.Build,
//            unSelectedIcon = Icons.Outlined.Build,
//        ),
//        BottomNavigationBarItem(
//            route = BottomNavigationItemsEnum.SETTINGS,
//            title = context.getString(R.string.settings),
//            selectedIcon = Icons.Filled.Settings,
//            unSelectedIcon = Icons.Outlined.Settings,
//        )
//    )
//
//    NavigationBar {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        screens.forEach { screen ->
//            NavigationBarItem(
//                modifier = modifier,
//                selected = screen.route.value == currentRoute,
//                onClick = {
//                    navController.navigate(screen.route.value) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                label = {
//                    Text(text = screen.title)
//                },
//                alwaysShowLabel = true,
//                icon = {
//                    Icon(
//                        imageVector = if (screen.route.value == currentRoute) {
//                            screen.selectedIcon
//                        } else {
//                            screen.unSelectedIcon
//                        },
//                        contentDescription = screen.title
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun NavigationGraph(
//    navController: NavHostController,
//) {
//    NavHost(
//        navController = navController,
//        startDestination = BottomNavigationItemsEnum.DAILY_COUNTER.value
//    ) {
////        composable(BottomNavigationItemsEnum.DAILY_COUNTER.value) {
////            DailyCounterScreen(navController = navController)
////        }
////        composable(BottomNavigationItemsEnum.TASKS.value) {
////            TasksScreen()
////        }
////        composable(BottomNavigationItemsEnum.CALENDAR.value) {
////            CalendarScreen()
////        }
////        composable(BottomNavigationItemsEnum.SETTINGS.value) {
////            SettingsScreen()
////        }
//        composable(
//            route = Screens.HomeScreen.route
//        ) {
//            HomeScreen()
//        }
//        composable(
//            route = Screens.SubmitReminderScreen.route + "/reminderId={reminderId}",
//            arguments = listOf(navArgument("reminderId") {
//                type = NavType.IntType
//                defaultValue = -1
//                nullable = false
//            })
//        ) { entry ->
//            SubmitReminderScreen(
//                navController = navController,
//                reminderId = entry.arguments?.getInt("reminderId") ?: -1
//            )
//        }
//    }
//}
//
//data class BottomNavigationBarItem(
//    val route: BottomNavigationItemsEnum,
//    val title: String,
//    val selectedIcon: ImageVector,
//    val unSelectedIcon: ImageVector,
//)
//
//enum class BottomNavigationItemsEnum(val value: String) {
//    DAILY_COUNTER("DailyCounter"),
//    TASKS("Tasks"),
//    CALENDAR("Calendar"),
//    SETTINGS("Settings"),
//}