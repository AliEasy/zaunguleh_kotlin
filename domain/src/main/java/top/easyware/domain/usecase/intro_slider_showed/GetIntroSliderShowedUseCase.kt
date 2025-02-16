package top.easyware.domain.usecase.intro_slider_showed

import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Inject

class GetIntroSliderShowedUseCase @Inject constructor(
    private val repository: SharedPreferencesRepository
) {
    fun invoke(): Boolean {
        return repository.getIntroSliderShowed()
    }
}