package top.easyware.zanguleh.core.database.reminder.domain.model

import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.PrimaryKey
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.util.UiText

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

@Composable
fun RemindRepeatType.toHumanReadable() : String {
    return when (this) {
        RemindRepeatType.DAILY -> UiText.StringResource(R.string.daily).asString()
        RemindRepeatType.WEEKLY -> UiText.StringResource(R.string.weekly).asString()
        RemindRepeatType.MONTHLY -> UiText.StringResource(R.string.monthly).asString()
        RemindRepeatType.YEARLY -> UiText.StringResource(R.string.yearly).asString()
    }
}

//fun RemindRepeatType.toHumanReadable(context: Context) : String {
//    return when (this) {
//        RemindRepeatType.DAILY -> UiText.StringResource(R.string.daily).asString(context)
//        RemindRepeatType.WEEKLY -> UiText.StringResource(R.string.weekly).asString(context)
//        RemindRepeatType.MONTHLY -> UiText.StringResource(R.string.monthly).asString(context)
//        RemindRepeatType.YEARLY -> UiText.StringResource(R.string.yearly).asString(context)
//    }
//}

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