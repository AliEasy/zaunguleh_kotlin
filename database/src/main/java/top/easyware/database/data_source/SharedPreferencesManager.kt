package top.easyware.database.data_source

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val lastVisitedTabKey = "last_visited_tab"

    fun saveHomeLastVisitedTab(tab: String) {
        sharedPreferences.edit().putString(lastVisitedTabKey, tab).apply()
    }

    fun getHomeLastVisitedTab(): String? {
        return sharedPreferences.getString(lastVisitedTabKey, null)
    }
}