package top.easyware.zanguleh.core.screens

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("HomeScreen")
    data object SubmitReminderScreen : Screens("SubmitReminderScreen")

    fun withOptionalArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            args.forEach {(key, value) -> append("/$key=$value")}
        }

//        var finalRoute = route
//        args.forEach { (key, value) -> finalRoute = finalRoute.replace("{$key}", value) }
//        return finalRoute
    }
}