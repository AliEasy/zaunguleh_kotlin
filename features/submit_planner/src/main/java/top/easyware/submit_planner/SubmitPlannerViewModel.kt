package top.easyware.submit_planner

import android.app.AlarmManager
import android.app.Notification
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import top.easyware.core.util.CalendarUtil
import top.easyware.domain.model.PlannerDto
import top.easyware.domain.model.PlannerTypeEnum
import top.easyware.domain.model.ReminderRepeatTypeEnum
import javax.inject.Inject

@HiltViewModel
class SubmitPlannerViewModel @Inject constructor(
    private val
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SubmitPlannerState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<SubmitPlannerEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("reminderId")?.let { reminderId ->
            if (reminderId != -1) {
                viewModelScope.launch {
                    fullReminderUseCase.getReminderByIdUseCase(reminderId)?.also { reminder ->
                        _state.value = _state.value.copy(
                            reminderId = reminder.reminderId,
                            title = _state.value.title.copy(
                                text = reminder.title,
                                isHintVisible = reminder.title.isBlank()
                            ),
                            description = _state.value.description.copy(
                                text = reminder.description ?: "",
                                isHintVisible = reminder.description.isNullOrBlank()
                            ),
                            dueDate = _state.value.dueDate.copy(
                                persianDate = reminder.reminderDueDatePersian,
                                gregorianDate = reminder.reminderDueDate,
                                isHintVisible = reminder.reminderDueDatePersian.isBlank()
                            ),
                            isImportant = reminder.isImportant ?: false,
                            remindDateTime = _state.value.remindDateTime.copy(
                                persianDate = reminder.remindDatePersian ?: "",
                                gregorianDate = reminder.remindDate ?: "",
                                time = reminder.remindTime ?: "",
                                isSelected = !reminder.remindDate.isNullOrBlank() && !reminder.remindTime.isNullOrBlank()
                            ),
                            remindRepeatType = _state.value.remindRepeatType.copy(
                                type = reminder.remindRepeatType,
                                isSelected = reminder.remindRepeatType != null
                            ),
                            isHereForInsert = false
                        )
                    }
                }
            } else {
                _state.value = state.value.copy(isHereForInsert = true)
            }
        }
    }

    fun onEvent(event: SubmitPlannerIntent) {
        when (event) {
            is SubmitPlannerIntent.EditReminderEnable -> {
                _state.value =
                    state.value.copy(isEditMode = true)
            }

            is SubmitPlannerIntent.EditReminderCancel -> {
                _state.value =
                    state.value.copy(isEditMode = false)
            }

            is SubmitPlannerIntent.DeleteReminder -> {
                deleteReminder()
            }

            is SubmitPlannerIntent.SubmitReminder -> {
                submitReminder()
            }

            is SubmitPlannerIntent.TitleChangeValue -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        text = event.value,
                        isHintVisible = event.value.isBlank()
                    ),
                )
                validateForm()
            }

            is SubmitPlannerIntent.DescriptionChangeValue -> {
                _state.value = _state.value.copy(
                    description = _state.value.description.copy(
                        text = event.value,
                        isHintVisible = event.value.isBlank()
                    )
                )
            }

            is SubmitPlannerIntent.IsImportantChange -> {
                _state.value = _state.value.copy(
                    isImportant = !_state.value.isImportant
                )
            }

            is SubmitPlannerIntent.DueDatePickerChange -> {
                _state.value = _state.value.copy(
                    dueDate = _state.value.dueDate.copy(
                        persianDate = event.persianDate,
                        gregorianDate = event.gregorianDate,
                        isHintVisible = event.persianDate.isBlank()
                    ),
                )
                validateForm()
            }

            is SubmitPlannerIntent.RemindDateTimePickerChange -> {
                _state.value = _state.value.copy(
                    remindDateTime = _state.value.remindDateTime.copy(
                        persianDate = event.persianDate,
                        gregorianDate = event.gregorianDate,
                        time = event.time,
                        isSelected = event.gregorianDate.isNotBlank() && event.time.isNotBlank()
                    )
                )
            }

            is SubmitPlannerIntent.RemindDateTimePickerClear -> {
                _state.value = _state.value.copy(
                    remindDateTime = _state.value.remindDateTime.copy(
                        persianDate = "",
                        gregorianDate = "",
                        time = "",
                        isSelected = false,
                    )
                )
            }

            is SubmitPlannerIntent.RemindRepeatTypeChange -> {
                _state.value = _state.value.copy(
                    remindRepeatType = _state.value.remindRepeatType.copy(
                        type = event.type,
                        isSelected = true
                    )
                )
            }

            is SubmitPlannerIntent.RemindRepeatTypeClear -> {
                _state.value = _state.value.copy(
                    remindRepeatType = _state.value.remindRepeatType.copy(
                        type = null,
                        isSelected = false
                    )
                )
            }

            is SubmitPlannerIntent.SetDescriptionHint -> {
                _state.value = _state.value.copy(
                    description = _state.value.description.copy(
                        hint = event.value
                    )
                )
            }

            is SubmitPlannerIntent.SetTitleHint -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        hint = event.value
                    )
                )
            }

            is SubmitPlannerIntent.SetPageTitle -> {
                _state.value = _state.value.copy(
                    pageTitle = event.value
                )
            }

            is SubmitPlannerIntent.SetAppbarDropdownExpanded -> {
                _state.value = _state.value.copy(
                    isAppbarDropdownExpanded = event.value ?: !_state.value.isAppbarDropdownExpanded
                )
            }

            is SubmitPlannerIntent.SetRepeatDropdownExpanded -> {
                _state.value = _state.value.copy(
                    isRepeatDropdownExpanded = event.value ?: !_state.value.isRepeatDropdownExpanded
                )
            }

            is SubmitPlannerIntent.SetShowRemindTimeDialog -> {
                _state.value = _state.value.copy(
                    showRemindTimeDialog = event.value ?: !_state.value.showRemindTimeDialog
                )
            }

            is SubmitPlannerIntent.SetShowSureDeleteDialog -> {
                _state.value = _state.value.copy(
                    showSureDeleteDialog = event.value ?: !_state.value.showSureDeleteDialog
                )
            }

            is SubmitPlannerIntent.SetTempRemindDate -> {
                _state.value = _state.value.copy(
                    tempRemindDate = event.value
                )
            }

            is SubmitPlannerIntent.SetTempRemindDatePersian -> {
                _state.value = _state.value.copy(
                    tempRemindDatePersian = event.value
                )
            }
        }
    }

    private fun validateForm() {
        val titleIsValid = formValidationUseCase.validateReminderTitleUseCase.execute(
            _state.value.title.text
        ).success
        val dueDateIsValid = formValidationUseCase.validateReminderDueDateUseCase.execute(
            _state.value.dueDate.persianDate
        ).success
        _state.value = _state.value.copy(
            formIsValid = titleIsValid && dueDateIsValid
        )
    }

    private fun submitReminder() {
        viewModelScope.launch {
            val result: Long = fullReminderUseCase.addReminderUseCase(
                PlannerDto(
                    plannerId = _state.value.reminderId,
                    title = _state.value.title.text,
                    type = PlannerTypeEnum.EVENT.value,
                    dueDatePersian = _state.value.dueDate.persianDate,
                    dueDate = _state.value.dueDate.gregorianDate,
                    description = _state.value.description.text,
                    isImportant = _state.value.isImportant,
                    reminderDatePersian = _state.value.remindDateTime.persianDate,
                    reminderDate = _state.value.remindDateTime.gregorianDate,
                    reminderTime = _state.value.remindDateTime.time,
                    reminderRepeatType = _state.value.remindRepeatType.type
                )
            )
            if (result > 0) {
                if (_state.value.remindDateTime.persianDate.isNotBlank() && _state.value.remindDateTime.time.isNotBlank()) {
                    _eventFlow.emit(
                        SubmitPlannerEvent.ScheduleReminder(
                            _state.value.remindDateTime.persianDate,
                            _state.value.remindDateTime.time,
                            _state.value.title.text,
                            result.toInt(),
                            _state.value.remindRepeatType.type
                        )
                    )
                }
                _eventFlow.emit(SubmitPlannerEvent.NavigateBack)
            } else {
                _eventFlow.emit(SubmitPlannerEvent.ShowSnackBar("error")) //todo
            }
        }
    }

    private fun deleteReminder() {
        viewModelScope.launch {
            if ((_state.value.reminderId ?: -1) != -1) {
                fullReminderUseCase.deleteReminderUseCase(_state.value.reminderId!!)
                _eventFlow.emit(SubmitPlannerEvent.NavigateBack)
            } else {
                _eventFlow.emit(SubmitPlannerEvent.ShowSnackBar("error")) //todo
            }
        }
    }

    fun scheduleReminder(
        context: Context,
        remindDate: String,
        remindTime: String,
        notificationTitle: String,
        notificationId: Int,
        repeatType: ReminderRepeatTypeEnum?
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
        val repeatType = ReminderRepeatTypeEnum.entries.find { it.value == repeatTypeStr }
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
            .setSmallIcon(R.drawable.ic_stat_name)
            .setColor(Color(ContextCompat.getColor(context, R.color.light_orange)).toArgb())
            .setContentTitle(notificationTitle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_ALARM)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setChannelId(channelId)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, builder.build())

        if (repeatType != null && mainRemindDate != null && mainRemindTime != null) {
            val nextJalaliDate = when (repeatType) {
                ReminderRepeatTypeEnum.DAILY -> {
                    CalendarUtil.addDaysToJalali(mainRemindDate, 1)
                }

                ReminderRepeatTypeEnum.WEEKLY -> {
                    CalendarUtil.addWeeksToJalali(mainRemindDate, 1)
                }

                ReminderRepeatTypeEnum.MONTHLY -> {
                    CalendarUtil.addMonthsToJalali(mainRemindDate, 1)
                }

                ReminderRepeatTypeEnum.YEARLY -> {
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