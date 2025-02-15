package top.easyware.uikit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun TouchDisable(
    modifier: Modifier = Modifier,
    disableTouch: Boolean,
    content: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(disableTouch) {
        if (disableTouch) {
            focusManager.clearFocus()
        }
    }

    Box(
        modifier = modifier
    ) {
        content()
        Box(
            modifier = Modifier.matchParentSize().then(
                if (disableTouch) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
                } else {
                    Modifier
                }
            )
        )
    }
}