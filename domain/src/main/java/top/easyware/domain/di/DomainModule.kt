package top.easyware.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import top.easyware.domain.repository.PlannerRepository
import top.easyware.domain.repository.SharedPreferencesRepository
import top.easyware.domain.usecase.planner.AddPlannerUseCase
import top.easyware.domain.usecase.planner.DeletePlannerUseCase
import top.easyware.domain.usecase.planner.GetAllPlannersUseCase
import top.easyware.domain.usecase.home_last_visited_tab.GetHomeLastVisitedTabUseCase
import top.easyware.domain.usecase.planner.GetPlannerByIdUseCase
import top.easyware.domain.usecase.home_last_visited_tab.SaveHomeLastVisitedTabUseCase
import top.easyware.domain.usecase.planner.FullPlannerUseCase
import top.easyware.domain.usecase.submit_planner_form_validation.SubmitPlannerFormValidationUseCase
import top.easyware.domain.usecase.submit_planner_form_validation.ValidatePlannerDueDateUseCase
import top.easyware.domain.usecase.submit_planner_form_validation.ValidatePlannerTitleUseCase
import javax.inject.Singleton

@Module
@InstallIn
object DomainModule {
    @Provides
    @Singleton
    fun provideFullPlannerUseCase(repository: PlannerRepository): FullPlannerUseCase {
        return FullPlannerUseCase(
            addPlannerUseCase = AddPlannerUseCase(repository),
            deletePlannerUseCase = DeletePlannerUseCase(repository),
            getAllPlannersUseCase = GetAllPlannersUseCase(repository),
            getPlannerByIdUseCase = GetPlannerByIdUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideSubmitPlannerFormValidationUseCase(): SubmitPlannerFormValidationUseCase {
        return SubmitPlannerFormValidationUseCase(
            validatePlannerTitleUseCase = ValidatePlannerTitleUseCase(),
            validatePlannerDueDateUseCase = ValidatePlannerDueDateUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideGetHomeLastVisitedTabUseCase(repository: SharedPreferencesRepository): GetHomeLastVisitedTabUseCase {
        return GetHomeLastVisitedTabUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideSaveHomeLastVisitedTabUseCase(repository: SharedPreferencesRepository): SaveHomeLastVisitedTabUseCase {
        return SaveHomeLastVisitedTabUseCase(repository = repository)
    }
}