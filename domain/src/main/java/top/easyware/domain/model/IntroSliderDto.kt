package top.easyware.domain.model

import top.easyware.core.util.UiText

data class IntroSliderDto (
    val imageRes: Int,
    val title: UiText,
    val description: UiText?
)