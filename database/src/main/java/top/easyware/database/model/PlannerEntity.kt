package top.easyware.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlannerEntity (
    @PrimaryKey val plannerId: Int,
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