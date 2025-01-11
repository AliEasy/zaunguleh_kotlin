package top.easyware.zanguleh.features.daily_counter.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Composable
fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: ReminderModel,
    onTap: () -> Unit
) {
    val context = LocalContext.current
//    val interactionSource = remember { MutableInteractionSource() }

    Box(
        Modifier
            .padding(vertical = 5.dp)
            .clickable {
                onTap()
            }
            .clip(RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.tertiary)
            .padding(vertical = 10.dp)
//            .indication(
//                interactionSource = interactionSource,
//                rememberRipple(
//                    color = Color.Cyan, radius = 5.dp, bounded = true
//                )
//            )
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.important_selected,
                    ),
                    contentDescription = context.getString(R.string.event_is_important)
                )
                Text(
                    text = reminder.title,
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp),
                )
                Spacer(modifier = Modifier.width(1.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = reminder.reminderDueDatePersian,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}