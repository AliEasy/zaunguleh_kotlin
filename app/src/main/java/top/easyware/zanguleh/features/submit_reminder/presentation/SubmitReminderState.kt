package top.easyware.zanguleh.features.submit_reminder.presentation

import top.easyware.zanguleh.features.submit_reminder.presentation.components.DatePickerState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.DateTimePickerState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.RemindRepeatState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.TextFieldState

data class SubmitReminderState(
    val reminderId: Int? = null,
    val isHereForInsert: Boolean = true,
    val isEditMode: Boolean = false,
    val title: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),
    val dueDate: DatePickerState = DatePickerState(),
    val isImportant: Boolean = false,
    val remindDateTime: DateTimePickerState = DateTimePickerState(),
    val remindRepeatType: RemindRepeatState = RemindRepeatState(),
    val pageTitle: String = "",
    val isAppbarDropdownExpanded: Boolean = false,
    val isRepeatDropdownExpanded: Boolean = false,
    val showSureDeleteDialog: Boolean = false,
    val showRemindTimeDialog: Boolean = false,
    val tempRemindDate: String = "",
    val tempRemindDatePersian: String = "",
    val formIsValid: Boolean = false,
)