package top.easyware.database.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.database.data_source.PlannerDao
import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val dao: PlannerDao
) : PlannerRepository {
    override fun getAllPlanners(): Flow<List<PlannerDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlannerById(id: Int): PlannerDto? {
        TODO("Not yet implemented")
    }

    override suspend fun addPlanner(planner: PlannerDto): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlanner(plannerId: Int) {
        TODO("Not yet implemented")
    }

}