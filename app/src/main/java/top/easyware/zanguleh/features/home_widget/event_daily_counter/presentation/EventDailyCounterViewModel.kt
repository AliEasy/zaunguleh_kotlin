package top.easyware.zanguleh.features.home_widget.event_daily_counter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import javax.inject.Inject

class EventDailyCounterViewModel @Inject constructor(
    private val fullReminderUseCase: FullReminderUseCase
) : ViewModel() {

    private var _eventList = MutableStateFlow<List<ReminderModel>>(emptyList())
    var eventList: StateFlow<List<ReminderModel>> = _eventList

    fun getEventList() {
        viewModelScope.launch {
            fullReminderUseCase.getRemindersUseCase().collect { result ->
                _eventList.value = result
            }
        }
    }
}