package top.easyware.zanguleh.features.home.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.zanguleh.features.home.data.preferences.HomeSharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeSharedPreferencesManager: HomeSharedPreferencesManager
) : ViewModel() {
    fun saveLastVisitedTab(tab: String) = homeSharedPreferencesManager.saveLastVisitedTab(tab)
    fun getLastVisitedTab() = homeSharedPreferencesManager.getLastVisitedTab()
}