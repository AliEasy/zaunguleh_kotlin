package top.easyware.zanguleh.features.daily_counter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.easyware.zanguleh.features.daily_counter.presentation.components.FilterSection
import top.easyware.zanguleh.features.daily_counter.presentation.components.ReminderItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCounterScreen(
    viewModel: DailyCounterViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(DailyCounterEvent.AddReminder)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add note"
                        )
                    },
                    text = {
                        Text(
                            text = "add",
                        )
                    },
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("tesxt")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(DailyCounterEvent.ToggleFilterSection)
                            }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Filter"
                            )
                        }
                    },
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
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxSize()
                        .padding(it),
                ) {
                    items(state.reminders) { reminder ->
                        ReminderItem(modifier = Modifier.fillMaxWidth(), reminder = reminder)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}