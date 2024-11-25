package top.easyware.zanguleh.core.database.reminder.domain.use_case

import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository

class DeleteReminderUseCase (private val repository: ReminderRepository) {
    suspend operator fun invoke(reminderId: Int) {
        repository.deleteReminder(reminderId)
    }
}