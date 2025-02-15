package top.easyware.domain.model

import top.easyware.core.util.UiText
import top.easyware.domain.R

enum class ReminderRepeatTypeEnum(val value: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly")
}

fun ReminderRepeatTypeEnum.toHumanReadable(): UiText {
    return when (this) {
        ReminderRepeatTypeEnum.DAILY -> UiText.StringResource(R.string.daily)
        ReminderRepeatTypeEnum.WEEKLY -> UiText.StringResource(R.string.weekly)
        ReminderRepeatTypeEnum.MONTHLY -> UiText.StringResource(R.string.monthly)
        ReminderRepeatTypeEnum.YEARLY -> UiText.StringResource(R.string.yearly)
    }
}