package top.easyware.event_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        getEventList()
    }

    private fun getEventList() {
        getRemindersJob?.cancel()
        getRemindersJob =
            fullPlannerUseCase.getAllPlannersUseCase().onEach { events ->
                _state.value = state.value.copy(
                    eventList = events,
                )
            }
                .launchIn(viewModelScope)
    }
}