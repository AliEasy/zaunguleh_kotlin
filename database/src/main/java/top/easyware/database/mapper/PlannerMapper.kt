package top.easyware.database.mapper

import top.easyware.database.model.PlannerEntity
import top.easyware.domain.model.PlannerDto

fun PlannerEntity.mapper(): PlannerDto {
    return PlannerDto(
        plannerId = plannerId,
        type = type,
        title = title,
        dueDate = dueDate,
        dueDatePersian = dueDatePersian,
        reminderDate = reminderDate,
        reminderDatePersian = reminderDatePersian,
        reminderTime = reminderTime,
        reminderRepeatType = reminderRepeatType,
        isImportant = isImportant,
        description = description,
    )
}

fun PlannerDto.mapper(): PlannerEntity {
    return PlannerEntity(
        plannerId = plannerId,
        type = type,
        title = title,
        dueDate = dueDate,
        dueDatePersian = dueDatePersian,
        reminderDate = reminderDate,
        reminderDatePersian = reminderDatePersian,
        reminderTime = reminderTime,
        reminderRepeatType = reminderRepeatType,
        isImportant = isImportant,
        description = description,
    )
}