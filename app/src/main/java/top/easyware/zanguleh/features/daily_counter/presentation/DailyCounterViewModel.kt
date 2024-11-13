package top.easyware.zanguleh.features.daily_counter.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import top.easyware.zanguleh.core.database.reminder.domain.util.ReminderFilter
import javax.inject.Inject

@HiltViewModel
class DailyCounterViewModel @Inject constructor(
    private val fullReminderUseCase: FullReminderUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DailyCounterState())
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

            is DailyCounterEvent.ToggleFilterSection -> {}
        }
    }

    private fun getReminders(reminderFilter: ReminderFilter?) {
        getRemindersJob?.cancel()
        getRemindersJob =
            fullReminderUseCase.getRemindersUseCase(reminderFilter).onEach { reminderModels ->
                _state.value = state.value.copy(
                    reminders = reminderModels,
                    reminderFilter = reminderFilter
                )
            }
                .launchIn(viewModelScope)
    }
}