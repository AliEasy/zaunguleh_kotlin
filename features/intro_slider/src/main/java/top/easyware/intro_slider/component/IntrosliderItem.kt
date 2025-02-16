package top.easyware.intro_slider.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import top.easyware.domain.model.IntroSliderDto

@Composable
fun IntroSliderItem(
    slider: IntroSliderDto,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(slider.imageRes),
            contentDescription = slider.title.asString(),
            modifier = Modifier.width(125.dp).height(125.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            slider.title.asString(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        if (slider.description != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                slider.description!!.asString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}