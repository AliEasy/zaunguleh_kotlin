package top.easyware.zanguleh.features.daily_counter.domain.use_case

import kotlinx.coroutines.flow.Flow
import top.easyware.zanguleh.features.daily_counter.domain.model.Reminder
import top.easyware.zanguleh.features.daily_counter.domain.repository.ReminderRepository

class GetAllRemindersUseCase(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getAllReminders()
    }
}