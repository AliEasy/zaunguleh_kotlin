package top.easyware.domain.model

data class PlannerDto (
    val plannerId: Int? = null,
    val type: String,
    val title: String,
    val dueDate: String,
    val dueDatePersian: String,
    val reminderDate: String? = null,
    val reminderDatePersian: String? = null,
    val reminderTime: String? = null,
    val reminderRepeatType: ReminderRepeatTypeEnum? = null,
    val isImportant: Boolean? = null,
    val description: String? = null,
)