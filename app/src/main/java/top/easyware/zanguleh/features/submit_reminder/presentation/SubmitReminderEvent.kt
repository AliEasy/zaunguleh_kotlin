package top.easyware.zanguleh.features.submit_reminder.presentation

sealed class SubmitReminderEvent {
    data object EditReminderEnable : SubmitReminderEvent()
    data object EditReminderCancel : SubmitReminderEvent()
    data object SubmitReminder : SubmitReminderEvent()
    data object DeleteReminder : SubmitReminderEvent()

}