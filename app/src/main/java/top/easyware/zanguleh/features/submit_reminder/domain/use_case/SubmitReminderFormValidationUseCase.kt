package top.easyware.zanguleh.features.submit_reminder.domain.use_case

data class SubmitReminderFormValidationUseCase(
    val validateReminderTitleUseCase: ValidateReminderTitleUseCase,
    val validateReminderDueDateUseCase: ValidateReminderDueDateUseCase,
)