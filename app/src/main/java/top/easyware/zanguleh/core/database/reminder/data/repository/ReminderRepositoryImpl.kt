package top.easyware.zanguleh.core.database.reminder.data.repository

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.core.database.reminder.data.data_source.ReminderDao
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val dao: ReminderDao
) : ReminderRepository {
    override fun getReminders(): Flow<List<ReminderModel>> {
        return dao.getReminders()
    }

    override suspend fun getReminderById(id: Int): ReminderModel? {
        return dao.getReminderById(id)
    }

    override suspend fun addReminder(reminder: ReminderModel): Long {
        return dao.addReminder(reminder)
    }

    override suspend fun deleteReminder(reminderId: Int) {
        dao.deleteReminder(reminderId)
    }

}