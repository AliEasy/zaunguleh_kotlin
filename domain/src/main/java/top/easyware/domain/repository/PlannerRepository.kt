package top.easyware.domain.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.domain.model.PlannerDto

interface PlannerRepository {
    fun getAllPlanners(): Flow<List<PlannerDto>>

    suspend fun getPlannerById(id: Int): PlannerDto?

    suspend fun addPlanner(planner: PlannerDto): Long

    suspend fun deletePlanner(plannerId: Int)
}