package top.easyware.zanguleh.features.submit_reminder.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import top.easyware.zanguleh.features.submit_reminder.presentation.components.DatePickerState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.SubmitReminderFieldsEvent
import top.easyware.zanguleh.features.submit_reminder.presentation.components.TextFieldState
import javax.inject.Inject

@HiltViewModel
class SubmitReminderViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val fullReminderUseCase: FullReminderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SubmitReminderState())
    val state = _state

    private val _title = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.event_title)
        )
    )
    val title = _title

    private val _description = mutableStateOf(
        TextFieldState(
            hint = context.getString(R.string.note)
        )
    )
    val description = _description

    private val _dueDate = mutableStateOf(
        DatePickerState()
    )
    val dueDate = _dueDate

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data object NavigateBack : UiEvent()
    }

    private var _reminderId: Int? = null

    init {
        savedStateHandle.get<Int>("reminderId")?.let { reminderId ->
            if (reminderId != -1) {
                viewModelScope.launch {
                    fullReminderUseCase.getReminderByIdUseCase(reminderId)?.also { reminder ->
                        _reminderId = reminder.reminderId
                        _title.value = title.value.copy(
                            text = reminder.title,
                            isHintVisible = reminder.title.isBlank()
                        )
                        _description.value = description.value.copy(
                            text = reminder.description ?: "",
                            isHintVisible = reminder.description?.isBlank() ?: true
                        )
                        _state.value = state.value.copy(isHereForInsert = false)
                    }
                }
            } else {
                _state.value = state.value.copy(isHereForInsert = true)
            }
        }
    }

    fun onEvent(event: SubmitReminderEvent) {
        when (event) {
            SubmitReminderEvent.EditReminderEnable -> _state.value =
                state.value.copy(isEditMode = true)

            SubmitReminderEvent.EditReminderCancel -> _state.value =
                state.value.copy(isEditMode = false)

            SubmitReminderEvent.DeleteReminder -> deleteReminder()
            SubmitReminderEvent.SubmitReminder -> submitReminder()
        }
    }

    private fun submitReminder() {
        viewModelScope.launch {
            fullReminderUseCase.addReminderUseCase(
                ReminderModel(
                    reminderId = _reminderId,
                    title = _title.value.text,
                    reminderType = "Occasion",
                    reminderDueDatePersian = _dueDate.value.persianDate,
                    reminderDueDate = _dueDate.value.gregorianDate,
                    description = _description.value.text
                )
            )
            _eventFlow.emit(UiEvent.NavigateBack)
        }
    }

    private fun deleteReminder() {
        viewModelScope.launch {
            if ((_reminderId ?: -1) != -1) {
                fullReminderUseCase.deleteReminderUseCase(_reminderId!!)
                _eventFlow.emit(UiEvent.NavigateBack)
            } else {
                _eventFlow.emit(UiEvent.ShowSnackBar("error")) //todo
            }
        }
    }

    fun onFieldsEvent(event: SubmitReminderFieldsEvent) {
        when (event) {
            is SubmitReminderFieldsEvent.OnTitleChangeValue -> {
                _title.value = title.value.copy(
                    text = event.value,
                    isHintVisible = event.value.isBlank()
                )
            }

            is SubmitReminderFieldsEvent.OnTitleChangeFocus -> {
//                _title.value = title.value.copy(
//                    isHintVisible = title.value.text.isBlank()
//                )
            }

            is SubmitReminderFieldsEvent.OnDescriptionChangeValue -> {
                _description.value = description.value.copy(
                    text = event.value,
                    isHintVisible = event.value.isBlank()
                )
            }

            is SubmitReminderFieldsEvent.OnDescriptionChangeFocus -> {
//                _description.value = description.value.copy(
//                    isHintVisible = description.value.text.isBlank()
//                )
            }

            is SubmitReminderFieldsEvent.OnIsImportantChange -> {

            }

            is SubmitReminderFieldsEvent.OnDueDatePickerChange -> {
                _dueDate.value = dueDate.value.copy(
                    persianDate = event.persianDate,
                    gregorianDate = event.gregorianDate,
                    isHintVisible = event.persianDate.isBlank()
                )
            }
        }
    }
}