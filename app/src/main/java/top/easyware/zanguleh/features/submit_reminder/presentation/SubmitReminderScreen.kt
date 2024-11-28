package top.easyware.zanguleh.features.submit_reminder.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = title)
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
                                contentDescription = context.getString(R.string.get_back)
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
                                        contentDescription = context.getString(R.string.operation)
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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ButtonComponent(
                                title = context.getString(R.string.submit_event),
                                onClick = {

                                },
                                buttonType = ButtonComponentType.Filled
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            ButtonComponent(
                                title = context.getString(R.string.cancel),
                                onClick = {
                                    viewModel.onEvent(SubmitReminderEvent.EditReminderCancel)
                                },
                                buttonType = ButtonComponentType.Outlined
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
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(start = 23.dp, end = 23.dp, top = 5.dp, bottom = 5.dp),
                ) {
                    TransparentHintTextField(
                        text = viewModel.title.value.text,
                        hint = viewModel.title.value.hint,
                        isHintVisible = viewModel.title.value.isHintVisible,
                        onValueChange = {
                            viewModel.onFieldsEvent(SubmitReminderFieldsEvent.OnTitleChangeValue(it))
                        },
                        onFocusChange = {
                            viewModel.onFieldsEvent(SubmitReminderFieldsEvent.OnTitleChangeFocus(it))
                        },
                        singleLine = true
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = MaterialTheme.colorScheme.tertiary)
                        .padding(19.dp)

                ) {
                    TransparentHintTextField(
                        text = viewModel.description.value.text,
                        hint = viewModel.description.value.hint,
                        isHintVisible = viewModel.description.value.isHintVisible,
                        onValueChange = {
                            viewModel.onFieldsEvent(
                                SubmitReminderFieldsEvent.OnDescriptionChangeValue(
                                    it
                                )
                            )
                        },
                        onFocusChange = {
                            viewModel.onFieldsEvent(
                                SubmitReminderFieldsEvent.OnDescriptionChangeFocus(
                                    it
                                )
                            )
                        },
                        singleLine = true
                    )
                }
            }
        }
    }
}