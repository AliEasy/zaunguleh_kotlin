package top.easyware.core.model

import top.easyware.core.util.UiText

data class ValidationResult (
    val isValid: Boolean,
    val errorMessage: UiText? = null
)