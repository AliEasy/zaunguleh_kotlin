package top.easyware.domain.usecase.home_last_visited_tab

import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SaveHomeLastVisitedTabUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun invoke(tab: String) {
        repository.saveHomeLastVisitedTab(tab = tab)
    }
}