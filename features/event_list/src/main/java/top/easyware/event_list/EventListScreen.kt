package top.easyware.event_list

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.easyware.core.util.UiText
import top.easyware.event_list.components.EventItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    viewModel: EventListViewModel = hiltViewModel(),
    navToSubmitPlannerScreen: (Int?) -> Unit
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
                        navToSubmitPlannerScreen(null)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = UiText.StringResource(R.string.new_event)
                                .asString(),
                        )
                    },
                    text = {
                        Text(
                            UiText.StringResource(R.string.new_event).asString(),
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
                            UiText.StringResource(R.string.event_list).asString(),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    },
                )
            },

            ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                if (state.eventList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                            .fillMaxSize()
                            .padding(it),
                    ) {
                        items(state.eventList) { event ->
                            EventItem(
                                modifier = Modifier.fillMaxWidth(),
                                planner = event,
                                onTap = {
                                    navToSubmitPlannerScreen(event.plannerId ?: -1)
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