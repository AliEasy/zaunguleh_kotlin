package top.easyware.zanguleh.features.submit_reminder.presentation.components

import androidx.compose.ui.focus.FocusState

sealed class SubmitReminderFieldsEvent {
    data class OnTitleChangeValue(val value: String) : SubmitReminderFieldsEvent()
    data class OnTitleChangeFocus(val focusState: FocusState) : SubmitReminderFieldsEvent()
    data class OnDescriptionChangeValue(val value: String) : SubmitReminderFieldsEvent()
    data class OnDescriptionChangeFocus(val focusState: FocusState) : SubmitReminderFieldsEvent()
    data class IsImportant(val isSelected: Boolean) : SubmitReminderFieldsEvent()
}