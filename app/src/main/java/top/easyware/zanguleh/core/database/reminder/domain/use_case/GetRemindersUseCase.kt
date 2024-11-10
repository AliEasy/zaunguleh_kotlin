package top.easyware.zanguleh.core.database.reminder.domain.use_case

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository

class GetRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(): Flow<List<ReminderModel>> {
        return repository.getReminders()
    }
}