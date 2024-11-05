package top.easyware.zanguleh.features.daily_counter.data.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.features.daily_counter.data.data_source.ReminderDao
import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder
import top.easyware.zanguleh.features.daily_counter.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val dao: ReminderDao
): ReminderRepository {
    override fun getAllReminders(): Flow<List<Reminder>> {
        return dao.getAllReminders()
    }

    override suspend fun getReminderById(id: Int): Reminder? {
        return dao.getReminderById(id);
    }

    override suspend fun addReminder(reminder: Reminder) {
        return dao.addReminder(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        return dao.deleteReminder(reminder)
    }
}