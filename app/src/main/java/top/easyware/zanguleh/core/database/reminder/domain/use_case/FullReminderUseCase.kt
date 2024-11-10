package top.easyware.zanguleh.core.database.reminder.domain.use_case

data class FullReminderUseCase(
    val getRemindersUseCase: GetRemindersUseCase,
    val getReminderByIdUseCase: GetReminderByIdUseCase,
    val addReminderUseCase: AddReminderUseCase,
    val deleteReminderUseCase: DeleteReminderUseCase,
)