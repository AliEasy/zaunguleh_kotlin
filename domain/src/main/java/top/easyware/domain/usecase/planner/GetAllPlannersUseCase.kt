package top.easyware.domain.usecase.planner

import kotlinx.coroutines.flow.Flow
import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class GetAllPlannersUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    operator fun invoke(): Flow<List<PlannerDto>> {
        return repository.getAllPlanners()
    }
}