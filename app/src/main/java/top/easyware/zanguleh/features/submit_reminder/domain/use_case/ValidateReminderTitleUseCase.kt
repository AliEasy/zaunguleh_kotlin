package top.easyware.zanguleh.features.submit_reminder.domain.use_case

import top.easyware.zanguleh.R
import top.easyware.zanguleh.core.generic.use_case.BaseUseCase
import top.easyware.zanguleh.core.util.UiText
import top.easyware.zanguleh.features.submit_reminder.domain.model.ValidationResult

class ValidateReminderTitleUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        return if (input.isBlank()) {
            ValidationResult(
                success = false,
                errorMessage = UiText.StringResource(resId = R.string.reminder_title_is_empty)
            )
        } else {
            ValidationResult(
                success = true
            )
        }
    }
}