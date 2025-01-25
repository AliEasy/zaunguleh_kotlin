package top.easyware.zanguleh.core.database.reminder.domain.model

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import top.easyware.zanguleh.R

enum class ReminderType(val value: String) {
    TASK("Task"),
    OCCASION("Occasion")
}

enum class RemindRepeatType(val value: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly")
}

fun RemindRepeatType.toHumanReadable(context: Context) : String {
    return when (this) {
        RemindRepeatType.DAILY -> context.getString(R.string.daily)
        RemindRepeatType.WEEKLY -> context.getString(R.string.weekly)
        RemindRepeatType.MONTHLY -> context.getString(R.string.monthly)
        RemindRepeatType.YEARLY -> context.getString(R.string.yearly)
    }
}

@Entity
data class ReminderModel(
    @PrimaryKey val reminderId: Int? = null,
    val title: String,
    val reminderType: String,
    val reminderDueDatePersian: String,
    val reminderDueDate: String,
    val remindDatePersian: String? = null,
    val remindDate: String? = null,
    val remindTime: String? = null,
    val remindRepeatType: RemindRepeatType? = null,
    val isImportant: Boolean? = null,
    val showInDailyCounter: Boolean? = null,
    val description: String? = null,
    val creationDataTime: String? = null
)