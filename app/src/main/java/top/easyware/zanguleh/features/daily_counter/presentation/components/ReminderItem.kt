package top.easyware.zanguleh.features.daily_counter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: ReminderModel,
    onTap: () -> Unit
) {
    Box(
        Modifier
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.tertiary)
            .padding(vertical = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onTap()
                    }
                )
            }
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = reminder.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
        }
    }
}