package top.easyware.intro_slider

import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import top.easyware.core.util.UiText
import top.easyware.domain.model.IntroSliderDto
import top.easyware.domain.usecase.intro_slider_showed.IntroSliderShowedUseCase
import javax.inject.Inject

@HiltViewModel
class IntroSliderViewModel @Inject constructor(
    private val introSliderShowedUseCase: IntroSliderShowedUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(IntroSliderState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<IntroSliderEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
        when (intent) {
            IntroSliderIntent.EnterApp -> {
                introSliderShowedUseCase.setIntroSliderShowedUseCase.invoke()
            }

            IntroSliderIntent.PermissionDenied -> {
                viewModelScope.launch {
                    _eventFlow.emit(IntroSliderEvent.PermissionDenied)
                }
            }
        }
    }
}