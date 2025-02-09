package top.easyware.database.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1
)
abstract class PlannerDatabase : RoomDatabase() {
    abstract val plannerDao : PlannerDao

    companion object {
        const val DATABASE_NAME = "planner_db"
    }
}