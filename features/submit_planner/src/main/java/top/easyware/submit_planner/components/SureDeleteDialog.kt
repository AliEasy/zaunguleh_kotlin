package top.easyware.submit_planner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import top.easyware.submit_planner.R
import top.easyware.uikit.ButtonComponent
import top.easyware.uikit.ButtonComponentType


@Composable
fun CustomDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = context.getString(R.string.sure_delete_event_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = context.getString(R.string.sure_delete_event_desc),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ButtonComponent(
                        title = context.getString(R.string.yes),
                        onClick = {
                            onConfirm()
                        },
                        buttonType = ButtonComponentType.Filled,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    ButtonComponent(
                        title = context.getString(R.string.no),
                        onClick = {
                            onDismiss()
                        },
                        buttonType = ButtonComponentType.Outlined,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}