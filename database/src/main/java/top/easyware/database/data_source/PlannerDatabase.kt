package top.easyware.database.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import top.easyware.database.model.PlannerEntity

@Database(
    entities = [PlannerEntity::class],
    version = 1
)
abstract class PlannerDatabase : RoomDatabase() {
    abstract val plannerDao : PlannerDao

    companion object {
        const val DATABASE_NAME = "planner_db"
    }
}