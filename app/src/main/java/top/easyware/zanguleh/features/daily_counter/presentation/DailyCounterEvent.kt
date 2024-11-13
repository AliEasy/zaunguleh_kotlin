package top.easyware.zanguleh.features.daily_counter.presentation

import top.easyware.zanguleh.core.database.reminder.domain.util.ReminderFilter

sealed class DailyCounterEvent {
    data class GetReminders(val reminderFilter: ReminderFilter?) : DailyCounterEvent()
    data object ToggleFilterSection : DailyCounterEvent()
}