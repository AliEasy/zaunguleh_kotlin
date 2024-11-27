package top.easyware.zanguleh.features.submit_reminder.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import top.easyware.zanguleh.core.uikit.ButtonComponent
import top.easyware.zanguleh.core.uikit.ButtonComponentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitReminderScreen(
    navController: NavController,
    viewModel: SubmitReminderViewModel = hiltViewModel(),
    reminderId: Int = -1
) {

    print(reminderId)

    val state = viewModel.state.value

    val isDropdownExpanded = remember { mutableStateOf(false) }

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
                        Text(text = "add")
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
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        if (!state.isEditMode)
                            IconButton(
                                onClick = {
                                    isDropdownExpanded.value = !isDropdownExpanded.value
                                })
                            {
                                Box {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "more"
                                    )
                                    DropdownMenu(
                                        expanded = isDropdownExpanded.value,
                                        onDismissRequest = { isDropdownExpanded.value = false }) {
                                        DropdownMenuItem(
                                            text = {
                                                Row {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "delete"
                                                    )
                                                    Text(text = "Delete")
                                                }
                                            },
                                            onClick = {
                                                viewModel.onEvent(
                                                    SubmitReminderEvent.DeleteReminder(
                                                        1
                                                    )
                                                )
                                            })
                                    }
                                }
                            }
                    }
                )
            },
            bottomBar = {
                if (state.isEditMode) {
                    BottomAppBar(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        ButtonComponent(
                            title = "ثبت",
                            onClick = {

                            },
                            modifier = Modifier,
                            buttonType = ButtonComponentType.Filled
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        ButtonComponent(
                            title = "انصراف",
                            onClick = {
                                viewModel.onEvent(SubmitReminderEvent.EditReminderCancel)
                            },
                            modifier = Modifier,
                            buttonType = ButtonComponentType.Text
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(it)
            ) {
                Text(text = "hellooooooo", color = Color.DarkGray)
            }
        }
    }
}