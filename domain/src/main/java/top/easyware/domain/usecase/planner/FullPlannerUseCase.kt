package top.easyware.domain.usecase.planner

data class FullPlannerUseCase (
    val addPlannerUseCase: AddPlannerUseCase,
    val deletePlannerUseCase: DeletePlannerUseCase,
    val getAllPlannersUseCase: GetAllPlannersUseCase,
    val getPlannerByIdUseCase: GetPlannerByIdUseCase
)