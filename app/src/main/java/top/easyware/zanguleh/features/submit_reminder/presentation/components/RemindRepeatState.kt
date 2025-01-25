package top.easyware.zanguleh.features.submit_reminder.presentation.components

import top.easyware.zanguleh.core.database.reminder.domain.model.RemindRepeatType

data class RemindRepeatState(
    val type: RemindRepeatType? = null,
    val isSelected: Boolean = false,
)