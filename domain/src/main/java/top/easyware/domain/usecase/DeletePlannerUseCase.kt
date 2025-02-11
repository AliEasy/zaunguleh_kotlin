package top.easyware.domain.usecase

import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class DeletePlannerUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend fun invoke(plannerId: Int) {
        repository.deletePlanner(plannerId = plannerId)
    }
}