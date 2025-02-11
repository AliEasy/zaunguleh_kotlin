package top.easyware.database.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.easyware.database.data_source.PlannerDatabase
import top.easyware.database.data_source.SharedPreferencesManager
import top.easyware.database.repository.PlannerRepositoryImpl
import top.easyware.database.repository.SharedPreferencesRepositoryImpl
import top.easyware.domain.repository.PlannerRepository
import top.easyware.domain.repository.SharedPreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePlannerDatabase(app: Application): PlannerDatabase {
        return Room.databaseBuilder(
            app,
            PlannerDatabase::class.java,
            PlannerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlannerRepository(db: PlannerDatabase): PlannerRepository {
        return PlannerRepositoryImpl(dao = db.plannerDao)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppSharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager {
        return SharedPreferencesManager(sharedPreferences = sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(sharedPreferencesManager: SharedPreferencesManager): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(
            sharedPreferencesManager = sharedPreferencesManager
        )
    }
}