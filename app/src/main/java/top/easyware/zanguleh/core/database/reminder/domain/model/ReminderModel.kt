package top.easyware.zanguleh.core.database.reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ReminderType(val value: String) {
    TASK("Task"),
    OCCASION("Occasion")
}

@Entity
data class ReminderModel(
    @PrimaryKey val reminderId: Int,
    val title: String,
    val reminderType: String,
    val reminderRepeat: Boolean?,
    val reminderDateTime: String?,
    val isImportant: Boolean?,
    val showInDailyCounter: Boolean?,
    val description: String?,
    val creationDataTime: String
)