package top.easyware.zanguleh.features.submit_reminder.presentation.components

data class DateTimePickerState(
    val persianDate: String = "",
    val gregorianDate: String = "",
    val time: String = "",
    val isSelected: Boolean = false,
)
