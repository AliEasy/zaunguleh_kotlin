package top.easyware.zanguleh.features.daily_counter.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder

@Database(
    entities = [Reminder::class],
    version = 1
)
abstract class ReminderDatabase : RoomDatabase() {
    abstract val reminderDao: ReminderDao
}