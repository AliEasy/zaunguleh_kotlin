package top.easyware.database.data_source

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val lastVisitedTabKey = "last_visited_tab"
    private val introSliderShowedKey = "intro_slider_showed"

    fun saveHomeLastVisitedTab(tab: String) {
        sharedPreferences.edit().putString(lastVisitedTabKey, tab).apply()
    }

    fun getHomeLastVisitedTab(): String? {
        return sharedPreferences.getString(lastVisitedTabKey, null)
    }

    fun setIntroSliderShowed() {
        sharedPreferences.edit().putBoolean(introSliderShowedKey, true).apply()
    }

    fun getIntroSliderShowed(): Boolean {
        return sharedPreferences.getBoolean(introSliderShowedKey, false)
    }
}