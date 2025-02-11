package top.easyware.database.repository

import top.easyware.database.data_source.SharedPreferencesManager
import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : SharedPreferencesRepository {
    override fun saveHomeLastVisitedTab(tab: String) {
        sharedPreferencesManager.saveHomeLastVisitedTab(tab = tab)
    }

    override fun getHomeLastVisitedTab(): String? {
        return sharedPreferencesManager.getHomeLastVisitedTab()
    }

}