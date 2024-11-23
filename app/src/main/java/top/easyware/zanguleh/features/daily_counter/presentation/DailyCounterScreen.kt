package top.easyware.zanguleh.features.daily_counter.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.easyware.zanguleh.features.daily_counter.presentation.components.FilterSection
import top.easyware.zanguleh.features.daily_counter.presentation.components.ReminderItem

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
                FloatingActionButton(onClick = {
                    viewModel.onEvent(DailyCounterEvent.AddReminder)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
                }
            },
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reminders",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(DailyCounterEvent.ToggleFilterSection)
                        }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Filter")
                    }
                }
                AnimatedVisibility(
                    visible = state.isFilterSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    FilterSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        filter = state.reminderFilter,
                        onFilterChanged = { reminderFilter ->
                            viewModel.onEvent(DailyCounterEvent.GetReminders(reminderFilter))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                ) {
                    items(state.reminders) { reminder ->
                        ReminderItem(modifier = Modifier.fillMaxWidth(), reminder = reminder)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
//            Column(
//                modifier = Modifier.padding(it),
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
////                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Reminders",
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                    IconButton(
//                        onClick = {
//                            viewModel.onEvent(DailyCounterEvent.ToggleFilterSection)
//                        }) {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Filter")
//                    }
//                }
//                AnimatedVisibility(
//                    visible = state.isFilterSectionVisible,
//                    enter = fadeIn() + slideInVertically(),
//                    exit = fadeOut() + slideOutVertically()
//                ) {
//                    FilterSection(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(vertical = 16.dp),
//                        filter = state.reminderFilter,
//                        onFilterChanged = { reminderFilter ->
//                            viewModel.onEvent(DailyCounterEvent.GetReminders(reminderFilter))
//                        }
//                    )
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Spacer(modifier = Modifier.height(16.dp))
//            }
        }
    }
}