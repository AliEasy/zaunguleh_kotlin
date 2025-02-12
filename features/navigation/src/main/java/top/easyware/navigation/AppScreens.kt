package top.easyware.navigation

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("HomeScreen")
    data object SubmitReminderScreen : AppScreens("SubmitReminderScreen")

    fun withOptionalArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            args.forEach {(key, value) -> append("/$key=$value")}
        }
    }
}