package top.easyware.intro_slider

import androidx.compose.foundation.pager.PagerState
import top.easyware.domain.model.IntroSliderDto

data class IntroSliderState(
    val introSliderPages: List<IntroSliderDto> = emptyList(),
)

sealed interface IntroSliderIntent {

}