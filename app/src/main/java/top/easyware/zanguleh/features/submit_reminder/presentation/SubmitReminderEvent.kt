package top.easyware.zanguleh.features.submit_reminder.presentation

import top.easyware.zanguleh.core.database.reminder.domain.model.RemindRepeatType

sealed interface SubmitReminderEvent {
    data object EditReminderEnable : SubmitReminderEvent
    data object EditReminderCancel : SubmitReminderEvent
    data object SubmitReminder : SubmitReminderEvent
    data object DeleteReminder : SubmitReminderEvent
    data class TitleChangeValue(val value: String) : SubmitReminderEvent
    data class DescriptionChangeValue(val value: String) : SubmitReminderEvent
    data object IsImportantChange : SubmitReminderEvent
    data class DueDatePickerChange(val persianDate: String, val gregorianDate : String) : SubmitReminderEvent
    data class RemindDateTimePickerChange(val persianDate: String, val gregorianDate : String, val time : String) : SubmitReminderEvent
    data object RemindDateTimePickerClear : SubmitReminderEvent
    data class RemindRepeatTypeChange(val type: RemindRepeatType) : SubmitReminderEvent
    data object RemindRepeatTypeClear : SubmitReminderEvent
    data class SetTitleHint(val value: String) : SubmitReminderEvent
    data class  SetDescriptionHint(val value: String) : SubmitReminderEvent
    data class  SetPageTitle(val value: String) : SubmitReminderEvent
}