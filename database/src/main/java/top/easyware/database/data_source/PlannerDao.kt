package top.easyware.database.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import top.easyware.database.model.PlannerEntity

@Dao
interface PlannerDao {
    @Query("SELECT * FROM plannerentity")
    fun getAllPlanners(): Flow<List<PlannerEntity>>

    @Query("SELECT * FROM plannerentity WHERE plannerId = :plannerId")
    suspend fun getPlannerById(plannerId: Int): PlannerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlanner(planner: PlannerEntity): Long

    @Query("DELETE FROM plannerentity WHERE plannerId = :plannerId")
    suspend fun deletePlanner(plannerId: Int)
}