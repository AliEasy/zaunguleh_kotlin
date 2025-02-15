package top.easyware.domain.usecase.submit_planner_form_validation

import top.easyware.core.model.BaseUseCase
import top.easyware.core.model.ValidationResult
import top.easyware.core.util.UiText
import top.easyware.domain.R

class ValidatePlannerTitleUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        return if (input.isBlank()) {
            ValidationResult(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.reminder_title_is_empty)
            )
        } else {
            ValidationResult(
                isValid = true
            )
        }
    }

}