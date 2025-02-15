package top.easyware.uikit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class ButtonComponentType {
    Filled,
    Text,
    Outlined
}

@Composable
fun ButtonComponent(
    title: String,
    onClick: () -> Unit,
    buttonType: ButtonComponentType = ButtonComponentType.Filled,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var buttonModifier = modifier
        .clip(RoundedCornerShape(10.dp))
        .height(55.dp)
        .fillMaxWidth()

    if (buttonType == ButtonComponentType.Outlined) {
        buttonModifier = buttonModifier.border(
            1.dp,
            Color(0xFF7D7D7D),
            shape = RoundedCornerShape(10.dp),
        )
    }

    Button(
        modifier = buttonModifier,
        content = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        onClick = onClick,
        colors = when (buttonType) {
            ButtonComponentType.Filled -> ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            else -> ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        },
        shape = RoundedCornerShape(10.dp),
        enabled = enabled
    )
}