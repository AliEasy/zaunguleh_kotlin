package top.easyware.zanguleh.features.submit_reminder.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import javax.inject.Inject

@HiltViewModel
class SubmitReminderViewModel @Inject constructor(
    private val fullReminderUseCase: FullReminderUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SubmitReminderState())
    val state = _state

    fun onEvent(event: SubmitReminderEvent) {
        when (event) {
            SubmitReminderEvent.EditReminder -> _state.value = state.value.copy(isEditMode = true)
            SubmitReminderEvent.EditReminderCancel -> _state.value =
                state.value.copy(isEditMode = false)

            is SubmitReminderEvent.DeleteReminder -> deleteReminder(event.reminderId)
        }
    }

    private fun deleteReminder(reminderId: Int) {
        viewModelScope.launch {
            fullReminderUseCase.deleteReminderUseCase(reminderId)
        }
    }
}