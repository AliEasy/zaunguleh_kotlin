package top.easyware.zanguleh.features.daily_counter.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAllReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder WHERE reminderId = :id")
    suspend fun getReminderById(id: Int): Reminder?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}