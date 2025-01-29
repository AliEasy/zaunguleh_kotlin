package top.easyware.zanguleh.features.home_widget.event_daily_counter.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.database.reminder.domain.model.ReminderModel

@Composable
fun ReminderItemWidget(
    modifier: GlanceModifier = GlanceModifier,
    reminder: ReminderModel,
    onTap: () -> Unit
) {
    val context = LocalContext.current

    Box(
        GlanceModifier
            .padding(vertical = 5.dp)
            .clickable {
                onTap()
            }
//            .clip(RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.tertiary)
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = GlanceModifier.width(1.dp).defaultWeight())
                Text(
                    text = reminder.title,
//                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp),
                    modifier = GlanceModifier.defaultWeight()
                )
                Image(
                    provider = ImageProvider(R.drawable.important_selected),
                    contentDescription = context.getString(R.string.event_is_important),
                    modifier = GlanceModifier.defaultWeight()
                )
            }
            Spacer(modifier = GlanceModifier.height(4.dp))
            Text(
                text = reminder.reminderDueDatePersian,
//                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}