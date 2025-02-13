package top.easyware.events

data class EventsState(
    val reminders: List<ReminderModel> = emptyList(),
    val reminderFilter: ReminderFilter? = null,
    val isFilterSectionVisible: Boolean = false
)

sealed class Event {
    data class GetReminders(val reminderFilter: ReminderFilter?) : DailyCounterEvent()
    data object ToggleFilterSection : DailyCounterEvent()
}