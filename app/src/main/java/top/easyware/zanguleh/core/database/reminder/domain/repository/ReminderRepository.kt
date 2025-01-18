package top.easyware.zanguleh.core.database.reminder.domain.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

interface ReminderRepository {
    fun getReminders(): Flow<List<ReminderModel>>

    suspend fun getReminderById(id: Int): ReminderModel?

    suspend fun addReminder(reminder: ReminderModel): Long

    suspend fun deleteReminder(reminderId: Int)
}