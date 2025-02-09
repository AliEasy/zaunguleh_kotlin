package top.easyware.domain.model

data class PlannerDto (
    val plannerId: Int,
    val title: String,
    val dueDate: String,
    val dueDatePersian: String,
    val reminderDate: String?,
    val reminderDatePersian: String?,
    val reminderTime: String?,
    val reminderRepeatType: String?,
    val isImportant: Boolean?,
    val description: String?,
)