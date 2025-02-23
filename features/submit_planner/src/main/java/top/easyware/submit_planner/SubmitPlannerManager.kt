package top.easyware.submit_planner

import top.easyware.domain.model.ReminderRepeatTypeEnum

data class SubmitPlannerState(
    val plannerId: Int?,
    val isHereForInsert: Boolean = true,
    val isEditMode: Boolean = false,
    val title: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),
    val dueDate: DatePickerState = DatePickerState(),
    val isImportant: Boolean = false,
    val reminderDateTime: DateTimePickerState = DateTimePickerState(),
    val reminderRepeatType: ReminderRepeatState = ReminderRepeatState(),
    val pageTitle: String = "",
    val isAppbarDropdownExpanded: Boolean = false,
    val isRepeatDropdownExpanded: Boolean = false,
    val showSureDeleteDialog: Boolean = false,
    val showReminderTimeDialog: Boolean = false,
    val tempReminderDate: String = "",
    val tempReminderDatePersian: String = "",
    val formIsValid: Boolean = false,
)

data class TextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
)

data class ReminderRepeatState(
    val type: ReminderRepeatTypeEnum? = null,
    val isSelected: Boolean = false,
)

data class DateTimePickerState(
    val persianDate: String = "",
    val gregorianDate: String = "",
    val time: String = "",
    val isSelected: Boolean = false,
)

data class DatePickerState(
    val persianDate: String = "",
    val gregorianDate: String = "",
    val isHintVisible: Boolean = true,
)

sealed interface SubmitPlannerIntent {
    data object EditReminderEnable : SubmitPlannerIntent
    data object EditReminderCancel : SubmitPlannerIntent
    data object SubmitReminder : SubmitPlannerIntent
    data object DeleteReminder : SubmitPlannerIntent
    data class TitleChangeValue(val value: String) : SubmitPlannerIntent
    data class DescriptionChangeValue(val value: String) : SubmitPlannerIntent
    data object IsImportantChange : SubmitPlannerIntent
    data class DueDatePickerChange(val persianDate: String, val gregorianDate: String) :
        SubmitPlannerIntent

    data class ReminderDateTimePickerChange(
        val persianDate: String,
        val gregorianDate: String,
        val time: String
    ) : SubmitPlannerIntent

    data object ReminderDateTimePickerClear : SubmitPlannerIntent
    data class ReminderRepeatTypeChange(val type: ReminderRepeatTypeEnum) : SubmitPlannerIntent
    data object ReminderRepeatTypeClear : SubmitPlannerIntent
    data class SetTitleHint(val value: String) : SubmitPlannerIntent
    data class SetDescriptionHint(val value: String) : SubmitPlannerIntent
    data class SetPageTitle(val value: String) : SubmitPlannerIntent
    data class SetAppbarDropdownExpanded(val value: Boolean?) : SubmitPlannerIntent
    data class SetRepeatDropdownExpanded(val value: Boolean?) : SubmitPlannerIntent
    data class SetShowSureDeleteDialog(val value: Boolean?) : SubmitPlannerIntent
    data class SetShowRemindTimeDialog(val value: Boolean?) : SubmitPlannerIntent
    data class SetTempReminderDate(val value: String) : SubmitPlannerIntent
    data class SetTempReminderDatePersian(val value: String) : SubmitPlannerIntent
}

sealed class SubmitPlannerEvent {
    data class ShowSnackBar(val message: String) : SubmitPlannerEvent()
    data class ScheduleReminder(
        val remindDate: String,
        val remindTime: String,
        val notificationTitle: String,
        val notificationId: Int,
        val repeatType: ReminderRepeatTypeEnum?
    ) : SubmitPlannerEvent()

    data object NavigateBack : SubmitPlannerEvent()
}