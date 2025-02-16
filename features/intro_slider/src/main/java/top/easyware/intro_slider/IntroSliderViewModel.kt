package top.easyware.intro_slider

import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import top.easyware.core.util.UiText
import top.easyware.domain.model.IntroSliderDto
import top.easyware.domain.usecase.intro_slider_showed.IntroSliderShowedUseCase
import javax.inject.Inject

@HiltViewModel
class IntroSliderViewModel @Inject constructor(
    private val introSliderShowedUseCase: IntroSliderShowedUseCase
) : ViewModel() {

    private val _state = mutableStateOf(IntroSliderState())
    val state = _state

    init {
        _state.value = _state.value.copy(
            introSliderPages = buildList {
                add(
                    IntroSliderDto(
                        R.drawable.event,
                        UiText.StringResource(R.string.zanguleh_desc),
                        null
                    )
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    add(
                        IntroSliderDto(
                            R.drawable.notification,
                            UiText.StringResource(R.string.notification_permission_title),
                            UiText.StringResource(R.string.notification_permission_desc),
                        )
                    )
            }
        )
    }

    fun onIntent(intent: IntroSliderIntent) {
//        when(intent) {
//
//        }
    }
}