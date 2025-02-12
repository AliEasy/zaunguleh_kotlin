package top.easyware.domain.usecase.planner

import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class GetPlannerByIdUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend operator  fun invoke(plannerId: Int): PlannerDto? {
        return repository.getPlannerById(plannerId = plannerId)
    }
}