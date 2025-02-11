package top.easyware.domain.usecase

import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class AddPlannerUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend fun invoke(input: PlannerDto): Long {
        return repository.addPlanner(planner = input)
    }
}