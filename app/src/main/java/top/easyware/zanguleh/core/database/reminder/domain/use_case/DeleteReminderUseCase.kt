package top.easyware.zanguleh.core.database.reminder.domain.use_case

import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository

class DeleteReminderUseCase (private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder: ReminderModel) {
        repository.deleteReminder(reminder)
    }
}