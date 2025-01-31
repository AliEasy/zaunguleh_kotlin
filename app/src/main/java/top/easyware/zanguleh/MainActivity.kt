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