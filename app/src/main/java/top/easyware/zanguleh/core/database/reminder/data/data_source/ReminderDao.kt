package top.easyware.zanguleh.core.database.reminder.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Dao
interface ReminderDao {
    @Query("SELECT * FROM remindermodel")
    fun getReminders(): Flow<List<ReminderModel>>

    @Query("SELECT * FROM remindermodel WHERE reminderId = :reminderId")
    suspend fun getReminderById(reminderId: Int): ReminderModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(reminder: ReminderModel)

    @Query("DELETE FROM remindermodel WHERE reminderId = :reminderId")
    suspend fun deleteReminder(reminderId: Int)
}