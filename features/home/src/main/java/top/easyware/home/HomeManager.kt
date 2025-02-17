package top.easyware.home

data class HomeState(
    val lastVisitedTab: String? = null
)

sealed interface HomeIntent {
    data class SaveLastVisitedTab(val tab: String) : HomeIntent
}