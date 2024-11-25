package top.easyware.zanguleh.features.submit_reminder.presentation

sealed class SubmitReminderEvent {
    data object EditReminder : SubmitReminderEvent()
    data object EditReminderCancel : SubmitReminderEvent()
    data class DeleteReminder(val reminderId: Int) : SubmitReminderEvent()

}