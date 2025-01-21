package top.easyware.zanguleh.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.easyware.zanguleh.core.database.reminder.data.data_source.ReminderDatabase
import top.easyware.zanguleh.core.database.reminder.data.repository.ReminderRepositoryImpl
import top.easyware.zanguleh.core.database.reminder.domain.repository.ReminderRepository
import top.easyware.zanguleh.core.database.reminder.domain.use_case.AddReminderUseCase
import top.easyware.zanguleh.core.database.reminder.domain.use_case.DeleteReminderUseCase
import top.easyware.zanguleh.core.database.reminder.domain.use_case.FullReminderUseCase
import top.easyware.zanguleh.core.database.reminder.domain.use_case.GetReminderByIdUseCase
import top.easyware.zanguleh.core.database.reminder.domain.use_case.GetRemindersUseCase
import top.easyware.zanguleh.features.home.data.preferences.HomeSharedPreferencesManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReminderDatabase(app: Application): ReminderDatabase {
        return Room.databaseBuilder(
            app,
            ReminderDatabase::class.java,
            ReminderDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderRepository(db: ReminderDatabase): ReminderRepository {
        return ReminderRepositoryImpl(db.reminderDao)
    }

    @Provides
    @Singleton
    fun provideReminderUseCase(repository: ReminderRepository): FullReminderUseCase {
        return FullReminderUseCase(
            getRemindersUseCase = GetRemindersUseCase(repository),
            addReminderUseCase = AddReminderUseCase(repository),
            deleteReminderUseCase = DeleteReminderUseCase(repository),
            getReminderByIdUseCase = GetReminderByIdUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideHomeSharedPreferencesManager(sharedPreferences: SharedPreferences): HomeSharedPreferencesManager {
        return HomeSharedPreferencesManager(sharedPreferences)
    }
}