package top.easyware.domain.usecase

import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class GetHomeLastVisitedTabUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun invoke(): String? {
        return repository.getHomeLastVisitedTab()
    }
}