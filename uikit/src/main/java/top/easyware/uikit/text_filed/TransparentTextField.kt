package top.easyware.uikit.text_filed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentTextField(
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    hintTextStyle: TextStyle = TextStyle(),
    singleLine: Boolean = true,
    isHintVisible: Boolean,
    maxLines: Int = 1,
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = maxLines,
            cursorBrush = SolidColor(textStyle.color)
        )
        if (isHintVisible) {
            Text(text = hint, style = hintTextStyle)
        }
    }
}