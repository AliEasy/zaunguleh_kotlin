package top.easyware.zanguleh.features.submit_reminder.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.flow.collectLatest
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.uikit.ButtonComponent
import top.easyware.zanguleh.core.uikit.ButtonComponentType
import top.easyware.zanguleh.features.submit_reminder.presentation.components.SubmitReminderFieldsEvent
import top.easyware.zanguleh.features.submit_reminder.presentation.components.TransparentHintTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitReminderScreen(
    navController: NavController,
    viewModel: SubmitReminderViewModel = hiltViewModel(),
    reminderId: Int = -1
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    val isDropdownExpanded = remember { mutableStateOf(false) }

    val title = if (reminderId == -1) {
        context.getString(R.string.new_event)
    } else {
        context.getString(R.string.edit_event)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SubmitReminderViewModel.UiEvent.NavigateBack -> {
                    navController.navigateUp()
                }

                is SubmitReminderViewModel.UiEvent.ShowSnackBar -> {

                }
            }
        }
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
                            text = title,
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                if (state.isEditMode) {
                                    viewModel.onEvent(SubmitReminderEvent.EditReminderCancel)
                                } else {
                                    navController.popBackStack()
                                }
                            })
                        {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = context.getString(R.string.get_back),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    },
                    actions = {
                        if (!state.isEditMode && !state.isHereForInsert)
                            IconButton(
                                onClick = {
                                    isDropdownExpanded.value = !isDropdownExpanded.value
                                })
                            {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = context.getString(R.string.operation),
                                        tint = MaterialTheme.colorScheme.onSecondary
                                    )
                                    DropdownMenu(
                                        expanded = isDropdownExpanded.value,
                                        onDismissRequest = { isDropdownExpanded.value = false }) {
                                        DropdownMenuItem(
                                            text = {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = context.getString(R.string.delete_event)
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(text = context.getString(R.string.delete_event))
                                                }
                                            },
                                            onClick = {
                                                viewModel.onEvent(
                                                    SubmitReminderEvent.DeleteReminder
                                                )
                                            },
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Edit,
                                                        contentDescription = context.getString(R.string.edit_event)
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(text = context.getString(R.string.edit_event))
                                                }
                                            },
                                            onClick = {
                                                viewModel.onEvent(
                                                    SubmitReminderEvent.EditReminderEnable
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
                                title = context.getString(R.string.submit_event),
                                onClick = {
                                    viewModel.onEvent(SubmitReminderEvent.SubmitReminder)
                                },
                                buttonType = ButtonComponentType.Filled,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            ButtonComponent(
                                title = context.getString(R.string.cancel),
                                onClick = {
                                    if (state.isHereForInsert) {
                                        navController.navigateUp()
                                    } else {
                                        viewModel.onEvent(SubmitReminderEvent.EditReminderCancel)
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
            Column(
                modifier = Modifier.padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(start = 23.dp, end = 23.dp, top = 15.dp, bottom = 15.dp),
                ) {
                    Column {
                        TransparentHintTextField(
                            text = viewModel.title.value.text,
                            hint = viewModel.title.value.hint,
                            isHintVisible = viewModel.title.value.isHintVisible,
                            onValueChange = {
                                viewModel.onFieldsEvent(
                                    SubmitReminderFieldsEvent.OnTitleChangeValue(
                                        it
                                    )
                                )
                            },
                            onFocusChange = {
                                viewModel.onFieldsEvent(
                                    SubmitReminderFieldsEvent.OnTitleChangeFocus(
                                        it
                                    )
                                )
                            },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
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
                                                    viewModel.onFieldsEvent(
                                                        SubmitReminderFieldsEvent.OnDueDatePickerChange(
                                                            persianDate = "${persianPickerDate.persianYear}/${persianPickerDate.persianMonth}/${persianPickerDate.persianDay}",
                                                            gregorianDate = "${persianPickerDate.gregorianYear}-${persianPickerDate.gregorianMonth}-${persianPickerDate.gregorianDay}"
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
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.calendar_outline),
                                contentDescription = context.getString(R.string.event_date),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (viewModel.dueDate.value.isHintVisible) {
                                Text(
                                    text = context.getString(R.string.event_date),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                )
                            } else {
                                Text(
                                    text = viewModel.dueDate.value.persianDate,
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
                            Modifier.clickable {
                                viewModel.onFieldsEvent(SubmitReminderFieldsEvent.OnIsImportantChange)
                            }
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(
                                    if (viewModel.isImportant.value.isImportant) R.drawable.important_selected
                                    else R.drawable.important_unselected
                                ),
                                contentDescription = context.getString(R.string.event_is_important)
                            )
                            Spacer(modifier = Modifier.width(11.dp))
                            Text(
                                text = context.getString(R.string.event_is_important),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (viewModel.isImportant.value.isImportant) Color.Black else Color.Gray
                            )

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
                                contentDescription = context.getString(R.string.note)
                            )
                            Spacer(modifier = Modifier.width(11.dp))
                            TransparentHintTextField(
                                text = viewModel.description.value.text,
                                hint = viewModel.description.value.hint,
                                isHintVisible = viewModel.description.value.isHintVisible,
                                onValueChange = {
                                    viewModel.onFieldsEvent(
                                        SubmitReminderFieldsEvent.OnDescriptionChangeValue(it)
                                    )
                                },
                                onFocusChange = {
                                    viewModel.onFieldsEvent(
                                        SubmitReminderFieldsEvent.OnDescriptionChangeFocus(it)
                                    )
                                },
                                singleLine = false,
                                textStyle = MaterialTheme.typography.labelLarge.copy(color = Color.Gray),
                                maxLines = 5
                            )
                        }
                    }
                }
            }
        }
    }
}