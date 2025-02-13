package top.easyware.event_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import top.easyware.domain.usecase.planner.FullPlannerUseCase
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val fullPlannerUseCase: FullPlannerUseCase
) : ViewModel() {

    private val _state = mutableStateOf(EventListState())
    val state = _state

    private var getRemindersJob: Job? = null

    init {
        getReminders(null)
    }

    fun onEvent(event: DailyCounterEvent) {

        when (event) {
            is DailyCounterEvent.GetReminders -> {
                getReminders(event.reminderFilter)
            }

            is DailyCounterEvent.ToggleFilterSection -> {
                _state.value = state.value.copy(isFilterSectionVisible = !state.value.isFilterSectionVisible)
            }
//            is DailyCounterEvent.AddReminder -> {
//                addDefReminder()
//            }
        }
    }

//    private fun addDefReminder() {
//        viewModelScope.launch {
//            fullReminderUseCase.addReminderUseCase(
//                ReminderModel(
//                    title = "Hi",
//                    reminderType = "Occasion"
//                )
//            )
//        }
//    }

    private fun getEventList() {
        getRemindersJob?.cancel()
        getRemindersJob =
            fullPlannerUseCase.getRemindersUseCase().onEach { reminderModels ->
                _state.value = state.value.copy(
                    reminders = reminderModels,
                    reminderFilter = reminderFilter
                )
            }
                .launchIn(viewModelScope)
    }
}