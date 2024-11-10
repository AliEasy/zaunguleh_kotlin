package top.easyware.zanguleh.core.database.reminder.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Database(
    entities = [ReminderModel::class],
    version = 1
)
abstract class ReminderDatabase : RoomDatabase() {
    abstract val reminderDao: ReminderDao

    companion object {
        const val DATABASE_NAME = "reminder_db"
    }
}