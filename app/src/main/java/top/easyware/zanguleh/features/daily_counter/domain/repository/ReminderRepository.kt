package top.easyware.zanguleh.features.daily_counter.domain.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder

interface ReminderRepository {
    fun getAllReminders(): Flow<List<Reminder>>

    suspend fun getReminderById(id: Int): Reminder?

    suspend fun addReminder(reminder: Reminder)

    suspend fun deleteReminder(reminder: Reminder)
}