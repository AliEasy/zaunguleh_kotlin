package top.easyware.zanguleh.core.uikit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ButtonComponentType {
    Filled,
    Text
}


@Composable
fun ButtonComponent(
    title: String,
    onClick: () -> Unit,
    buttonType: ButtonComponentType = ButtonComponentType.Filled,
    modifier: Modifier,
) {
    val buttonModifier = modifier
        .fillMaxWidth()
        .height(50.dp)

    Button(
        modifier = buttonModifier,
        content = {
            Text(
                text = title
            )
        },
        onClick = onClick,
        colors = when (buttonType) {
            ButtonComponentType.Filled -> ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ButtonComponentType.Text -> ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        }
    )
}