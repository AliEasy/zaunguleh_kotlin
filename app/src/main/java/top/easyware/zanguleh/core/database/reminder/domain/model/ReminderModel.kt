package top.easyware.zanguleh.core.database.reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ReminderType(val value: String) {
    TASK("Task"),
    OCCASION("Occasion")
}

@Entity
data class ReminderModel(
    @PrimaryKey val reminderId: Int? = null,
    val title: String,
    val reminderType: String,
    val reminderDueDatePersian: String,
    val reminderDueDate: String,
    val reminderRepeat: Boolean? = null,
    val remindDatePersian: String? = null,
    val isImportant: Boolean? = null,
    val showInDailyCounter: Boolean? = null,
    val description: String? = null,
    val creationDataTime: String? = null
)