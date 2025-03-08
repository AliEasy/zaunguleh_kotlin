package top.easyware.submit_planner

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.flow.collectLatest
import top.easyware.core.util.UiText
import top.easyware.domain.model.ReminderRepeatTypeEnum
import top.easyware.domain.model.toHumanReadable
import top.easyware.submit_planner.components.CustomDialog
import top.easyware.uikit.ButtonComponent
import top.easyware.uikit.ButtonComponentType
import top.easyware.uikit.TouchDisable
import top.easyware.uikit.dialog.TimePickerDialog
import top.easyware.uikit.text_filed.TransparentTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitPlannerScreen(
    viewModel: SubmitPlannerViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SubmitPlannerEvent.NavigateBack -> {
                    onPopBackStack()
                }

                is SubmitPlannerEvent.ShowSnackBar -> {

                }

                is SubmitPlannerEvent.ScheduleReminder -> {
                    viewModel.scheduleReminder(
                        context,
                        event.remindDate,
                        event.remindTime,
                        event.notificationTitle,
                        event.notificationId,
                        event.repeatType
                    )
                }

                is SubmitPlannerEvent.CancelReminder -> {
                    viewModel.cancelReminder(
                        context,
                        event.notificationId
                    )
                }
            }
        }
    }

    BackHandler(
        enabled = state.isEditMode
    ) {
        viewModel.onIntent(SubmitPlannerIntent.EditPlannerCancel)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    title = {
                        Text(
                            text = state.pageTitle.asString(),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (state.isEditMode) {
                                    viewModel.onIntent(SubmitPlannerIntent.EditPlannerCancel)
                                } else {
                                    onPopBackStack()
                                }
                            })
                        {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = UiText.StringResource(R.string.get_back)
                                    .asString(),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    },
                    actions = {
                        if (!state.isEditMode && !state.isHereForInsert)
                            IconButton(
                                onClick = {
                                    viewModel.onIntent(
                                        SubmitPlannerIntent.SetAppbarDropdownExpanded(
                                            null
                                        )
                                    )
                                })
                            {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = UiText.StringResource(R.string.operation)
                                            .asString(),
                                        tint = MaterialTheme.colorScheme.onSecondary
                                    )
                                    DropdownMenu(
                                        expanded = state.isAppbarDropdownExpanded,
                                        onDismissRequest = {
                                            viewModel.onIntent(
                                                SubmitPlannerIntent.SetAppbarDropdownExpanded(
                                                    false
                                                )
                                            )
                                        }) {
                                        DropdownMenuItem(
                                            text = {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Edit,
                                                        contentDescription = UiText.StringResource(R.string.edit_event)
                                                            .asString()
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(
                                                        text = UiText.StringResource(R.string.edit_event)
                                                            .asString()
                                                    )
                                                }
                                            },
                                            onClick = {
                                                viewModel.onIntent(
                                                    SubmitPlannerIntent.EditPlannerEnable
                                                )
                                            },
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = UiText.StringResource(R.string.delete_event)
                                                            .asString()
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(
                                                        text = UiText.StringResource(R.string.delete_event)
                                                            .asString()
                                                    )
                                                }
                                            },
                                            onClick = {
                                                viewModel.onIntent(
                                                    SubmitPlannerIntent.SetShowSureDeleteDialog(
                                                        true
                                                    )
                                                )
                                                viewModel.onIntent(
                                                    SubmitPlannerIntent.SetAppbarDropdownExpanded(
                                                        false
                                                    )
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                    }
                )
            },
            bottomBar = {
                if (state.isEditMode || state.isHereForInsert) {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ButtonComponent(
                                title = UiText.StringResource(R.string.submit_event).asString(),
                                onClick = {
                                    viewModel.onIntent(SubmitPlannerIntent.SubmitPlanner)
                                },
                                buttonType = ButtonComponentType.Filled,
                                modifier = Modifier.weight(1f),
                                enabled = state.formIsValid
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            ButtonComponent(
                                title = UiText.StringResource(R.string.cancel).asString(),
                                onClick = {
                                    if (state.isHereForInsert) {
                                        onPopBackStack()
                                    } else {
                                        viewModel.onIntent(SubmitPlannerIntent.EditPlannerCancel)
                                    }
                                },
                                buttonType = ButtonComponentType.Outlined,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        ) {
            TouchDisable(
                modifier = Modifier.padding(it),
                disableTouch = !state.isEditMode && !state.isHereForInsert
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(start = 23.dp, end = 23.dp, top = 15.dp, bottom = 15.dp),
                    ) {
                        Column {
                            TransparentTextField(
                                text = state.title.text,
                                hint = UiText.StringResource(R.string.event_title)
                                    .asString(context),
                                isHintVisible = state.title.isHintVisible,
                                onValueChange = { value ->
                                    viewModel.onIntent(
                                        SubmitPlannerIntent.TitleChangeValue(
                                            value
                                        )
                                    )
                                },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSecondary
                                ),
                                hintTextStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val picker = PersianDatePickerDialog(context)
                                            .setPositiveButtonString("باشه")
                                            .setNegativeButton("بیخیال")
                                            .setTodayButton("امروز")
                                            .setTodayButtonVisible(true)
                                            .setMinYear(1300)
                                            .setMaxYear(1405)
                                            .setMaxMonth(12)
                                            .setMaxDay(29)
                                            //                                    .setActionTextColor(Color.Gray.)
                                            //                                    .setTypeFace(typeface)
                                            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                                            .setListener(
                                                object : PersianPickerListener {
                                                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                                                        viewModel.onIntent(
                                                            SubmitPlannerIntent.DueDatePickerChange(
                                                                persianDate = "${persianPickerDate.persianYear}/${
                                                                    persianPickerDate.persianMonth
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }/${
                                                                    persianPickerDate.persianDay
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }",
                                                                gregorianDate = "${persianPickerDate.gregorianYear}-${
                                                                    persianPickerDate.gregorianMonth
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }-${
                                                                    persianPickerDate.gregorianDay
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }"
                                                                //                                                            gregorianDate = persianPickerDate.gregorianDate.toString()
                                                                //                                                            gregorianDate = SimpleDateFormat(
                                                                //                                                                "yyyy-MM-dd",
                                                                //                                                                Locale.ENGLISH
                                                                //                                                            ).format(
                                                                //                                                                persianPickerDate.gregorianDate
                                                                //                                                            )
                                                            )
                                                        )
                                                    }

                                                    override fun onDismissed() {}
                                                }
                                            )
                                        picker.show()
                                    }
                                    .padding(5.dp)
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.calendar_outline),
                                    contentDescription = UiText.StringResource(R.string.event_date)
                                        .asString(),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                if (state.dueDate.isHintVisible) {
                                    Text(
                                        text = UiText.StringResource(R.string.event_date)
                                            .asString(),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    )
                                } else {
                                    Text(
                                        text = state.dueDate.persianDate,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )
                                    )
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .padding(19.dp)

                    ) {
                        Column {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onIntent(SubmitPlannerIntent.IsImportantChange)
                                    }
                                    .padding(5.dp)
                            ) {
                                Image(
                                    imageVector = ImageVector.vectorResource(
                                        if (state.isImportant) R.drawable.important_selected
                                        else R.drawable.important_unselected
                                    ),
                                    contentDescription = UiText.StringResource(R.string.event_is_important)
                                        .asString()
                                )
                                Spacer(modifier = Modifier.width(11.dp))
                                Text(
                                    text = UiText.StringResource(R.string.event_is_important)
                                        .asString(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = if (state.isImportant) Color.Black else Color.Gray
                                )

                            }
                            Spacer(modifier = Modifier.height(9.dp))
                            Divider(
                                color = Color.LightGray,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val picker = PersianDatePickerDialog(context)
                                            .setPositiveButtonString("باشه")
                                            .setNegativeButton("بیخیال")
                                            .setTodayButton("امروز")
                                            .setTodayButtonVisible(true)
                                            .setMinYear(1300)
                                            .setMaxYear(1405)
                                            .setMaxMonth(12)
                                            .setMaxDay(29)
                                            //                                    .setActionTextColor(Color.Gray.)
                                            //                                    .setTypeFace(typeface)
                                            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                                            .setListener(
                                                object : PersianPickerListener {
                                                    override fun onDateSelected(
                                                        persianPickerDate: PersianPickerDate
                                                    ) {
                                                        viewModel.onIntent(
                                                            SubmitPlannerIntent.SetTempReminderDate(
                                                                "${persianPickerDate.gregorianYear}-${
                                                                    persianPickerDate.gregorianMonth
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }-${
                                                                    persianPickerDate.gregorianDay
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }"
                                                            )
                                                        )
                                                        viewModel.onIntent(
                                                            SubmitPlannerIntent.SetTempReminderDatePersian(
                                                                "${persianPickerDate.persianYear}/${
                                                                    persianPickerDate.persianMonth
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }/${
                                                                    persianPickerDate.persianDay
                                                                        .toString()
                                                                        .padStart(2, '0')
                                                                }"
                                                            )
                                                        )
                                                        viewModel.onIntent(
                                                            SubmitPlannerIntent.SetShowRemindTimeDialog(
                                                                true
                                                            )
                                                        )
                                                    }

                                                    override fun onDismissed() {}
                                                }
                                            )
                                        picker.show()
                                    }
                                    .padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(
                                            R.drawable.bell
                                        ),
                                        contentDescription = UiText.StringResource(R.string.event_reminder_date_time)
                                            .asString(),
                                        colorFilter = ColorFilter.tint(if (state.reminderDateTime.isSelected) Color.Black else Color.Gray)
                                    )
                                    Column {
                                        Row {
                                            Spacer(modifier = Modifier.width(11.dp))
                                            Text(
                                                text = UiText.StringResource(R.string.event_reminder_date_time)
                                                    .asString(),
                                                style = MaterialTheme.typography.labelLarge,
                                                color = if (state.reminderDateTime.isSelected) Color.Black else Color.Gray
                                            )
                                        }
                                        if (state.reminderDateTime.isSelected) {
                                            Row {
                                                Spacer(modifier = Modifier.width(11.dp))
                                                Text(
                                                    text = state.reminderDateTime.time,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = Color.Green
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = state.reminderDateTime.persianDate,
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = Color.Green
                                                )
                                            }
                                        }
                                    }
                                }
                                if (state.reminderDateTime.isSelected) {
                                    IconButton(
                                        onClick = {
                                            viewModel.onIntent(SubmitPlannerIntent.ReminderDateTimePickerClear)
                                            viewModel.onIntent(SubmitPlannerIntent.ReminderRepeatTypeClear)
                                        },
                                    )
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = UiText.StringResource(R.string.clear)
                                                .asString(),
                                            tint = Color.Cyan
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(9.dp))
                            Divider(
                                color = Color.LightGray,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = state.reminderDateTime.isSelected) {
                                        viewModel.onIntent(
                                            SubmitPlannerIntent.SetRepeatDropdownExpanded(
                                                true
                                            )
                                        )
                                    }
                                    .padding(5.dp)
                                    .alpha(if (state.reminderDateTime.isSelected) 1f else 0.5f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        imageVector = ImageVector.vectorResource(
                                            R.drawable.repeat_fill
                                        ),
                                        contentDescription = UiText.StringResource(R.string.event_reminder_repeat)
                                            .asString(),
                                        colorFilter = ColorFilter.tint(if (state.reminderRepeatType.isSelected) Color.Black else Color.Gray)
                                    )
                                    Column {
                                        Box {
                                            Row {
                                                Spacer(modifier = Modifier.width(11.dp))
                                                Text(
                                                    text = UiText.StringResource(R.string.event_reminder_repeat)
                                                        .asString(),
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = if (state.reminderRepeatType.isSelected) Color.Black else Color.Gray
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = state.isRepeatDropdownExpanded,
                                                onDismissRequest = {
                                                    viewModel.onIntent(
                                                        SubmitPlannerIntent.SetRepeatDropdownExpanded(
                                                            false
                                                        )
                                                    )
                                                }) {
                                                for (i in ReminderRepeatTypeEnum.entries) {
                                                    DropdownMenuItem(
                                                        text = {
                                                            Row {
                                                                Text(
                                                                    text = i.toHumanReadable()
                                                                        .asString()
                                                                )
                                                            }
                                                        },
                                                        onClick = {
                                                            viewModel.onIntent(
                                                                SubmitPlannerIntent.ReminderRepeatTypeChange(
                                                                    type = i
                                                                )
                                                            )
                                                            viewModel.onIntent(
                                                                SubmitPlannerIntent.SetRepeatDropdownExpanded(
                                                                    false
                                                                )
                                                            )
                                                        },
                                                    )
                                                }
                                            }
                                        }
                                        if (state.reminderRepeatType.isSelected) {
                                            Row {
                                                Spacer(modifier = Modifier.width(11.dp))
                                                Text(
                                                    text = "${
                                                        UiText.StringResource(R.string.repeat_in)
                                                            .asString()
                                                    } ${
                                                        state.reminderRepeatType.type!!.toHumanReadable()
                                                            .asString()
                                                    }",
                                                    style = MaterialTheme.typography.labelLarge,
                                                    color = Color.Green
                                                )
                                            }
                                        }
                                    }
                                }
                                if (state.reminderRepeatType.isSelected) {
                                    IconButton(
                                        onClick = {
                                            viewModel.onIntent(SubmitPlannerIntent.ReminderRepeatTypeClear)
                                        },
                                    )
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = UiText.StringResource(R.string.clear)
                                                .asString(),
                                            tint = Color.Cyan
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(9.dp))
                            Divider(
                                color = Color.LightGray,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 15.dp)
                            )
                            Spacer(modifier = Modifier.height(9.dp))
                            Row {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.note_outline),
                                    contentDescription = UiText.StringResource(R.string.note)
                                        .asString(),
                                    colorFilter = ColorFilter.tint(if (state.description.isHintVisible) Color.Gray else Color.Black)
                                )
                                Spacer(modifier = Modifier.width(11.dp))
                                TransparentTextField(
                                    text = state.description.text,
                                    hint = UiText.StringResource(R.string.note).asString(context),
                                    isHintVisible = state.description.isHintVisible,
                                    onValueChange = { value ->
                                        viewModel.onIntent(
                                            SubmitPlannerIntent.DescriptionChangeValue(value)
                                        )
                                    },
                                    singleLine = false,
                                    textStyle = MaterialTheme.typography.labelLarge.copy(color = Color.Black),
                                    maxLines = 5,
                                    hintTextStyle = MaterialTheme.typography.labelLarge.copy(color = Color.Gray),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showSureDeleteDialog) {
        CustomDialog(
            onConfirm = {
                viewModel.onIntent(SubmitPlannerIntent.DeletePlanner)
                viewModel.onIntent(SubmitPlannerIntent.SetShowSureDeleteDialog(false))
            },
            onDismiss = {
                viewModel.onIntent(SubmitPlannerIntent.SetShowSureDeleteDialog(false))
            }
        )
    }
    if (state.showReminderTimeDialog) {
        TimePickerDialog(
            onConfirm = { hour, minute ->
                viewModel.onIntent(
                    SubmitPlannerIntent.ReminderDateTimePickerChange(
                        persianDate = state.tempReminderDatePersian,
                        gregorianDate = state.tempReminderDate,
                        time = String.format("%02d:%02d", hour, minute)
                    )
                )
                viewModel.onIntent(SubmitPlannerIntent.SetShowRemindTimeDialog(false))
            },
            onDismiss = {
                viewModel.onIntent(SubmitPlannerIntent.SetShowRemindTimeDialog(false))
            }
        )
    }
}