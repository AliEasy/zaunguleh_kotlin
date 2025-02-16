package top.easyware.intro_slider

import top.easyware.domain.model.IntroSliderDto

data class IntroSliderState(
    val introSliderPages: List<IntroSliderDto> = emptyList(),
)

sealed interface IntroSliderIntent {
    data object EnterApp : IntroSliderIntent
}

sealed interface IntroSliderEvent {
    data object PermissionDenied : IntroSliderEvent
}