package top.easyware.zanguleh.features.home.data.preferences

import android.content.SharedPreferences
import top.easyware.zanguleh.features.home.presentation.BottomNavigationItemsEnum

class HomeSharedPreferencesManager(private val sharedPreferences: SharedPreferences) {
    fun saveLastVisitedTab(tab: String) {
        sharedPreferences.edit().putString("last_visited_tab", tab).apply()
    }

    fun getLastVisitedTab(): String {
        return sharedPreferences.getString("last_visited_tab", BottomNavigationItemsEnum.DAILY_COUNTER.value) ?: BottomNavigationItemsEnum.DAILY_COUNTER.value
    }
}