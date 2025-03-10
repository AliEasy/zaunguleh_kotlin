package top.easyware.domain.usecase.planner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import top.easyware.core.util.CalendarUtil
import top.easyware.domain.model.PlannerDto
import top.easyware.domain.repository.PlannerRepository
import javax.inject.Inject

class GetUpcomingRemindersUseCase @Inject constructor(
    private val repository: PlannerRepository
) {
    operator fun invoke(): Flow<List<PlannerDto>> {
        return repository.getAllPlanners()
            .map { planners ->
                val currentTime = System.currentTimeMillis()
                planners.filter { planner ->
                    val reminderDateMillis = planner.reminderDate?.let { CalendarUtil.convertDateToMillis(it) } ?: 0L
                    val reminderTimeMillis = planner.reminderTime?.let { CalendarUtil.convertTimeToMillis(it) } ?: 0L
                    val reminderDateTime = reminderDateMillis + reminderTimeMillis

                    reminderDateTime > currentTime
                }
            }
    }
}