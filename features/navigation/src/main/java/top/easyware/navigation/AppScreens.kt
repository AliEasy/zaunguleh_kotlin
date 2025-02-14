package top.easyware.navigation

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("HomeScreen")
    data object SubmitPlannerScreen : AppScreens("SubmitPlannerScreen")
    fun withOptionalArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            args.forEach {(key, value) -> append("/$key=$value")}
        }
    }
}