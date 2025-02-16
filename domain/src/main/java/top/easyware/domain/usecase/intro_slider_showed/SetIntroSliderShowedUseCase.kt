package top.easyware.domain.usecase.intro_slider_showed

import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class SetIntroSliderShowedUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun invoke() {
        repository.setIntroSliderShowed()
    }
}