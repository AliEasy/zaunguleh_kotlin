package top.easyware.uikit.dialog

import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import top.easyware.core.util.UiText
import top.easyware.uikit.ButtonComponent
import top.easyware.uikit.ButtonComponentType
import top.easyware.uikit.R

@Composable
fun TimePickerDialog(
    onConfirm: (hour: Int, minute: Int) -> Unit,
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
                    text = "انتخاب زمان",
                    style = MaterialTheme.typography.headlineSmall
                )

                var selectedHour by remember { mutableStateOf(12) }
                var selectedMinute by remember { mutableStateOf(0) }

                TimePicker(context,)
                AndroidView(
                    factory = { context ->
                        TimePicker(context).apply {
                            setIs24HourView(false)
                            setOnTimeChangedListener { _, hour, minute ->
                                selectedHour = hour
                                selectedMinute = minute
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ButtonComponent(
                        title = UiText.StringResource(R.string.yes).asString(),
                        onClick = {
                            onConfirm(selectedHour, selectedMinute)
                        },
                        buttonType = ButtonComponentType.Filled,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    ButtonComponent(
                        title = UiText.StringResource(R.string.no).asString(),
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