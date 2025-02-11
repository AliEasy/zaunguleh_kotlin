package top.easyware.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import top.easyware.domain.repository.PlannerRepository
import top.easyware.domain.repository.SharedPreferencesRepository
import top.easyware.domain.usecase.AddPlannerUseCase
import top.easyware.domain.usecase.DeletePlannerUseCase
import top.easyware.domain.usecase.GetAllPlannersUseCase
import top.easyware.domain.usecase.GetHomeLastVisitedTabUseCase
import top.easyware.domain.usecase.GetPlannerByIdUseCase
import top.easyware.domain.usecase.SaveHomeLastVisitedTabUseCase
import javax.inject.Singleton

@Module
@InstallIn
object DomainModule {
    @Provides
    @Singleton
    fun provideAddPlannerUseCase(repository: PlannerRepository): AddPlannerUseCase {
        return AddPlannerUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideDeletePlannerUseCase(repository: PlannerRepository): DeletePlannerUseCase {
        return DeletePlannerUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetAllPlannersUseCase(repository: PlannerRepository): GetAllPlannersUseCase {
        return GetAllPlannersUseCase(repository = repository)
    }

    @Provides
    @Singleton
    fun provideGetPlannerByIdUseCase(repository: PlannerRepository): GetPlannerByIdUseCase {
        return GetPlannerByIdUseCase(repository = repository)
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