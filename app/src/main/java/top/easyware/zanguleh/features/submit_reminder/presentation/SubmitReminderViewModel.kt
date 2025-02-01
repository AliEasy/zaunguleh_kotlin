package top.easyware.zanguleh.features.submit_reminder.presentation

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.database.reminder.domain.model.RemindRepeatType
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import top.easyware.zanguleh.core.util.CalendarUtil
import top.easyware.zanguleh.features.submit_reminder.presentation.components.DatePickerState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.DateTimePickerState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.IsImportantState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.RemindRepeatState
import top.easyware.zanguleh.features.submit_reminder.presentation.components.SubmitReminderFieldsEvent
import top.easyware.zanguleh.features.submit_reminder.presentation.components.TextFieldState
import javax.inject.Inject

@HiltViewModel
class SubmitReminderViewModel @Inject constructor(
    private val fullReminderUseCase: FullReminderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SubmitReminderState())
    val state = _state

    private val _title = mutableStateOf(
        TextFieldState()
    )
    val title = _title

    private val _description = mutableStateOf(
        TextFieldState()
    )
    val description = _description

    private val _dueDate = mutableStateOf(
        DatePickerState()
    )
    val dueDate = _dueDate

    private val _isImportant = mutableStateOf(
        IsImportantState()
    )
    val isImportant = _isImportant

    private val _remindDateTime = mutableStateOf(
        DateTimePickerState()
    )
    val remindDateTime = _remindDateTime

    private val _remindRepeatType = mutableStateOf(
        RemindRepeatState()
    )
    val remindRepeatType = _remindRepeatType


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data class ScheduleReminder(
            val remindDate: String,
            val remindTime: String,
            val notifTitle: String,
            val notifId: Int,
            val repeatType: RemindRepeatType?
        ) : UiEvent()

        data object NavigateBack : UiEvent()
    }

    private var _reminderId: Int? = null

    fun setTitleHint(hint: String) {
        _title.value = title.value.copy(hint = hint)
    }

    fun setDescriptionHint(hint: String) {
        _description.value = _description.value.copy(hint = hint)
    }

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
                        _dueDate.value = dueDate.value.copy(
                            persianDate = reminder.reminderDueDatePersian,
                            gregorianDate = reminder.reminderDueDate,
                            isHintVisible = reminder.reminderDueDatePersian.isBlank()
                        )
                        _isImportant.value = isImportant.value.copy(
                            isImportant = reminder.isImportant ?: false
                        )
                        _remindDateTime.value = remindDateTime.value.copy(
                            persianDate = reminder.remindDatePersian ?: "",
                            gregorianDate = reminder.remindDate ?: "",
                            time = reminder.remindTime ?: "",
                            isSelected = (reminder.remindDate?.isNotBlank()
                                ?: false) && (reminder.remindTime?.isNotBlank() ?: false)
                        )
                        _remindRepeatType.value = remindRepeatType.value.copy(
                            type = reminder.remindRepeatType,
                            isSelected = reminder.remindRepeatType != null
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
            SubmitReminderEvent.EditReminderEnable -> {
                _state.value =
                    state.value.copy(isEditMode = true)
            }

            SubmitReminderEvent.EditReminderCancel -> {
                _state.value =
                    state.value.copy(isEditMode = false)
            }

            SubmitReminderEvent.DeleteReminder -> {
                deleteReminder()
            }

            SubmitReminderEvent.SubmitReminder -> {
                submitReminder()
            }
        }
    }

    private fun submitReminder() {
        viewModelScope.launch {
            val result: Long = fullReminderUseCase.addReminderUseCase(
                ReminderModel(
                    reminderId = _reminderId,
                    title = _title.value.text,
                    reminderType = "Occasion",
                    reminderDueDatePersian = _dueDate.value.persianDate,
                    reminderDueDate = _dueDate.value.gregorianDate,
                    description = _description.value.text,
                    isImportant = _isImportant.value.isImportant,
                    remindDatePersian = _remindDateTime.value.persianDate,
                    remindDate = _remindDateTime.value.gregorianDate,
                    remindTime = _remindDateTime.value.time,
                    remindRepeatType = _remindRepeatType.value.type
                )
            )
            if (result > 0) {
                if (_remindDateTime.value.persianDate.isNotBlank() && _remindDateTime.value.time.isNotBlank()) {
                    _eventFlow.emit(
                        UiEvent.ScheduleReminder(
                            _remindDateTime.value.persianDate,
                            _remindDateTime.value.time,
                            _title.value.text,
                            result.toInt(),
                            _remindRepeatType.value.type
                        )
                    )
                }
                _eventFlow.emit(UiEvent.NavigateBack)
            } else {
                _eventFlow.emit(UiEvent.ShowSnackBar("error")) //todo
            }
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
                _isImportant.value = isImportant.value.copy(
                    isImportant = !isImportant.value.isImportant
                )
            }

            is SubmitReminderFieldsEvent.OnDueDatePickerChange -> {
                _dueDate.value = dueDate.value.copy(
                    persianDate = event.persianDate,
                    gregorianDate = event.gregorianDate,
                    isHintVisible = event.persianDate.isBlank()
                )
            }

            is SubmitReminderFieldsEvent.OnRemindDateTimePickerChange -> {
                _remindDateTime.value = remindDateTime.value.copy(
                    persianDate = event.persianDate,
                    gregorianDate = event.gregorianDate,
                    time = event.time,
                    isSelected = event.gregorianDate.isNotBlank() && event.time.isNotBlank()
                )
            }

            is SubmitReminderFieldsEvent.OnRemindDateTimePickerClear -> {
                _remindDateTime.value = remindDateTime.value.copy(
                    persianDate = "",
                    gregorianDate = "",
                    time = "",
                    isSelected = false,
                )
            }

            is SubmitReminderFieldsEvent.OnRemindRepeatTypeChange -> {
                _remindRepeatType.value = remindRepeatType.value.copy(
                    type = event.type,
                    isSelected = true
                )
            }

            is SubmitReminderFieldsEvent.OnRemindRepeatTypeClear -> {
                _remindRepeatType.value =
                    remindRepeatType.value.copy(
                        type = null,
                        isSelected = false,
                    )
            }
        }
    }

    fun scheduleReminder(
        context: Context,
        remindDate: String,
        remindTime: String,
        notificationTitle: String,
        notificationId: Int,
        repeatType: RemindRepeatType?
    ) {
        val calendar = CalendarUtil.convertJalaliToGregorian(remindDate, remindTime)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("notificationTitle", notificationTitle)
            putExtra("notificationId", notificationId)
            if (repeatType != null) {
                putExtra("repeatTypeStr", repeatType.value)
                putExtra("mainRemindDate", remindDate)
                putExtra("mainRemindTime", remindTime)
            }
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                requestExactAlarmPermission(context)
            }
        }
    }

    private fun requestExactAlarmPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }
}

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationTitle =
            intent?.getStringExtra("notificationTitle") ?: context.getString(R.string.reminder)
        val notificationId = intent?.getIntExtra("notificationId", 0) ?: 0
        val repeatTypeStr = intent?.getStringExtra("repeatTypeStr")
        val repeatType = RemindRepeatType.entries.find { it.value == repeatTypeStr }
        val mainRemindDate = intent?.getStringExtra("mainRemindDate")
        val mainRemindTime = intent?.getStringExtra("mainRemindTime")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val channelId = "reminder_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.reminders),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notificationTitle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())

        if (repeatType != null && mainRemindDate != null && mainRemindTime != null) {
            val nextJalaliDate = when(repeatType) {
                RemindRepeatType.DAILY -> {
                    CalendarUtil.addDaysToJalali(mainRemindDate, 1)
                }
                RemindRepeatType.WEEKLY -> {
                    CalendarUtil.addWeeksToJalali(mainRemindDate, 1)
                }
                RemindRepeatType.MONTHLY -> {
                    CalendarUtil.addMonthsToJalali(mainRemindDate, 1)
                }
                RemindRepeatType.YEARLY -> {
                    CalendarUtil.addYearsToJalali(mainRemindDate, 1)
                }
            }
            val nextGregorianCalendar =
                CalendarUtil.convertJalaliToGregorian(nextJalaliDate, mainRemindTime)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val newIntent = Intent(context, ReminderReceiver::class.java).apply {
                putExtra("notificationTitle", notificationTitle)
                putExtra("notificationId", notificationId)
                putExtra("repeatTypeStr", repeatType.value)
                putExtra("mainRemindDate", nextJalaliDate)
                putExtra("mainRemindTime", mainRemindTime)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextGregorianCalendar.timeInMillis,
                pendingIntent
            )
        }
    }
}