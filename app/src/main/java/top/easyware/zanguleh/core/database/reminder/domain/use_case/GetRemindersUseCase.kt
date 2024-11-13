package top.easyware.zanguleh.core.database.reminder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository
import top.easyware.zanguleh.core.database.reminder.domain.util.ReminderFilter

class GetRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(reminderFilter: ReminderFilter?): Flow<List<ReminderModel>> {
        return repository.getReminders().map { reminders ->
            when (reminderFilter) {
                is ReminderFilter.IsImportantFilter -> reminders.filter { reminderModel ->
                    reminderModel.isImportant ?: false
                }

                is ReminderFilter.ReminderTypeFilter -> reminders.filter { reminderModel -> reminderModel.reminderType == reminderFilter.reminderType.value }

                else -> reminders
            }
        }
    }
}