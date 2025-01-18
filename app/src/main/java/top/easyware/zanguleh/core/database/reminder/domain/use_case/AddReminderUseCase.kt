package top.easyware.zanguleh.core.database.reminder.domain.use_case

import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository

class AddReminderUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder: ReminderModel): Long {
        return repository.addReminder(reminder)
    }
}