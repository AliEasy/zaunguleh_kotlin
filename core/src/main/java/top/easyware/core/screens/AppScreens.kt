package top.easyware.core.screens

import kotlinx.serialization.Serializable

object AppScreens {
    @Serializable
    object Home

    @Serializable
    data class SubmitPlanner(val plannerId: Int?)

    @Serializable
    object IntroSlider
}