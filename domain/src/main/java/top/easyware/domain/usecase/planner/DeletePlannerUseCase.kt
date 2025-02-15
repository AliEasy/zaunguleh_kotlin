package top.easyware.domain.usecase.planner

import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class DeletePlannerUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend operator fun invoke(plannerId: Int) {
        repository.deletePlanner(plannerId = plannerId)
    }
}