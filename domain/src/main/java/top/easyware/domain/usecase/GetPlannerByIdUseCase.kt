package top.easyware.domain.usecase

import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class GetPlannerByIdUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend fun invoke(plannerId: Int): PlannerDto? {
        return repository.getPlannerById(plannerId = plannerId)
    }
}