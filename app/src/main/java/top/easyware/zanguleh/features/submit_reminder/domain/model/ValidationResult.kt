package top.easyware.zanguleh.features.submit_reminder.domain.model

import top.easyware.zanguleh.core.util.UiText

data class ValidationResult(
    val success: Boolean,
    val errorMessage: UiText? = null
)