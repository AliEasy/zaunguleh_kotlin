package top.easyware.zanguleh.features.daily_counter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.screens.Screens
import top.easyware.zanguleh.core.util.UiText
import top.easyware.zanguleh.features.daily_counter.presentation.components.FilterSection
import top.easyware.zanguleh.features.daily_counter.presentation.components.ReminderItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCounterScreen(
    navController: NavController,
    viewModel: DailyCounterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state = viewModel.state.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.SubmitReminderScreen.withOptionalArgs(mapOf("reminderId" to "-1")))
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = context.getString(R.string.new_event)
                        )
                    },
                    text = {
                        Text(
                            text = context.getString(R.string.new_event),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    title = {
                        Text(
                            context.getString(R.string.daily_counter),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                viewModel.onEvent(DailyCounterEvent.ToggleFilterSection)
//                            }) {
//                            Icon(
//                                imageVector = ImageVector.vectorResource(R.drawable.filter_outline),
//                                contentDescription = context.getString(R.string.filter),
//                                tint = MaterialTheme.colorScheme.onSecondary
//                            )
//                        }
//                    },
                )
            },

            ) {
            Column {
                AnimatedVisibility(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    visible = state.isFilterSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    FilterSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        filter = state.reminderFilter,
                        onFilterChanged = { reminderFilter ->
                            viewModel.onEvent(
                                DailyCounterEvent.GetReminders(
                                    reminderFilter
                                )
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (state.reminders.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        items(state.reminders) { reminder ->
                            ReminderItem(
                                modifier = Modifier.fillMaxWidth(),
                                reminder = reminder,
                                onTap = {
                                    navController.navigate(
                                        Screens.SubmitReminderScreen.withOptionalArgs(
                                            mapOf(
                                                "reminderId" to reminder.reminderId.toString()
                                            )
                                        )
                                    )
                                },
                            )
                        }
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            UiText.StringResource(R.string.nothing_found_to_show).asString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .wrapContentHeight(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}