package top.easyware.zanguleh.features.submit_reminder.presentation.components

import androidx.compose.ui.focus.FocusState

sealed class SubmitReminderFieldsEvent {
    data class OnTitleChangeValue(val value: String) : SubmitReminderFieldsEvent()
    data class OnTitleChangeFocus(val focusState: FocusState) : SubmitReminderFieldsEvent()
    data class OnDescriptionChangeValue(val value: String) : SubmitReminderFieldsEvent()
    data class OnDescriptionChangeFocus(val focusState: FocusState) : SubmitReminderFieldsEvent()
    data object OnIsImportantChange : SubmitReminderFieldsEvent()
    data class OnDueDatePickerChange(val persianDate: String, val gregorianDate : String) : SubmitReminderFieldsEvent()
    data class OnRemindDateTimePickerChange(val persianDate: String, val gregorianDate : String, val time : String) : SubmitReminderFieldsEvent()
}