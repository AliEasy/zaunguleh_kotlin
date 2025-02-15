package top.easyware.domain.usecase.planner

import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class AddPlannerUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    suspend operator fun invoke(input: PlannerDto): Long {
        return repository.addPlanner(planner = input)
    }
}