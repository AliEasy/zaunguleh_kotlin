package top.easyware.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import top.easyware.domain.model.ReminderRepeatTypeEnum

@Entity
data class PlannerEntity (
    @PrimaryKey val plannerId: Int? = null,
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