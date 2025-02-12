package top.easyware.domain.usecase.submit_planner_form_validation

data class SubmitPlannerFormValidationUseCase(
    val validatePlannerTitleUseCase: ValidatePlannerTitleUseCase,
    val validatePlannerDueDateUseCase: ValidatePlannerDueDateUseCase
)