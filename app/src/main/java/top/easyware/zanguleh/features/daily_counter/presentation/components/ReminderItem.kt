package top.easyware.zanguleh.features.daily_counter.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: ReminderModel,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = reminder.title, style = MaterialTheme.typography.bodyLarge, color = androidx.compose.ui.graphics.Color.Black)
    }
}