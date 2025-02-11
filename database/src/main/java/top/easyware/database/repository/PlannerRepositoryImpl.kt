package top.easyware.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.easyware.database.data_source.PlannerDao
import top.easyware.database.mapper.mapper
import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class PlannerRepositoryImpl @Inject constructor(
    private val dao: PlannerDao
) : PlannerRepository {
    override fun getAllPlanners(): Flow<List<PlannerDto>> {
        return dao.getAllPlanners().map { list -> list.map { it.mapper() } }
    }

    override suspend fun getPlannerById(plannerId: Int): PlannerDto? {
        return dao.getPlannerById(plannerId = plannerId)?.mapper()
    }

    override suspend fun addPlanner(planner: PlannerDto): Long {
        return dao.addPlanner(planner = planner.mapper())
    }

    override suspend fun deletePlanner(plannerId: Int) {
        dao.deletePlanner(plannerId = plannerId)
    }
}