package top.easyware.zanguleh.features.daily_counter.domain.use_case

data class ReminderUseCase(
    val getAllReminder: GetAllRemindersUseCase,
    val addReminderUseCase: AddReminderUseCase
)
