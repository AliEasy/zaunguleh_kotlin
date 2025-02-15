package top.easyware.event_list

import top.easyware.domain.model.PlannerDto

data class EventListState(
    val eventList: List<PlannerDto> = emptyList(),
)