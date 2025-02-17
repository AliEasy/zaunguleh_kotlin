package top.easyware.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.domain.usecase.intro_slider_showed.IntroSliderShowedUseCase
import javax.inject.Inject


@HiltViewModel
class AppNavHostViewModel @Inject constructor(
    private val introSliderShowedUseCase: IntroSliderShowedUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(AppNavHostManager())
    val state = _state

    init {
        introSliderShowedUseCase.getIntroSliderShowedUseCase.invoke().let {
            _state.value = _state.value.copy(
                introSliderShowed = it
            )
        }
    }
}