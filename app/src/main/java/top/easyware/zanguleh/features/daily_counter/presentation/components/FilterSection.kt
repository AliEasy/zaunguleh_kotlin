package top.easyware.zanguleh.features.daily_counter.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderType
import top.easyware.zanguleh.core.database.reminder.domain.util.ReminderFilter

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    filter: ReminderFilter? = null,
    onFilterChanged: (ReminderFilter) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "1",
                selected = filter is ReminderFilter.IsImportantFilter,
                onSelect = { onFilterChanged(ReminderFilter.IsImportantFilter) },
                modifier = modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "2",
                selected = filter is ReminderFilter.ReminderTypeFilter && filter.reminderType == ReminderType.TASK,
                onSelect = { onFilterChanged(ReminderFilter.ReminderTypeFilter(reminderType = ReminderType.TASK)) },
                modifier = modifier
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "3",
                selected = filter is ReminderFilter.ReminderTypeFilter && filter.reminderType == ReminderType.OCCASION,
                onSelect = { onFilterChanged(ReminderFilter.ReminderTypeFilter(reminderType = ReminderType.OCCASION)) },
                modifier = modifier
            )
        }
    }
}