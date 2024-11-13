package top.easyware.zanguleh.features.daily_counter.presentation

import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.util.ReminderFilter

data class DailyCounterState(
    val reminders: List<ReminderModel> = emptyList(),
    val reminderFilter: ReminderFilter? = null,
    val isFilterSectionVisible: Boolean = false
)