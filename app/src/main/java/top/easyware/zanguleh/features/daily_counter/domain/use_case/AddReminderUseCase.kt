package top.easyware.zanguleh.features.daily_counter.domain.use_case

import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder
import top.easyware.zanguleh.features.daily_counter.domain.repository.ReminderRepository

class AddReminderUseCase(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.addReminder(reminder)
    }
}