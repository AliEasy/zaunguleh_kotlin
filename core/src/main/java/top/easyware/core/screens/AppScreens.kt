package top.easyware.core.screens

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens("HomeScreen")
    data object SubmitPlannerScreen : AppScreens("SubmitPlannerScreen")
    data object IntroSliderScreen : AppScreens("IntroSliderScreen")
    fun withOptionalArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            args.forEach {(key, value) -> append("/$key=$value")}
        }
    }
}