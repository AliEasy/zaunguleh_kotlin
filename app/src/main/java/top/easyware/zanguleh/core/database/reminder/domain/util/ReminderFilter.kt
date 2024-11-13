package top.easyware.zanguleh.core.database.reminder.domain.util

import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderType

sealed class ReminderFilter {
    data object IsImportantFilter : ReminderFilter()
    data class ReminderTypeFilter(val reminderType: ReminderType) : ReminderFilter()
}