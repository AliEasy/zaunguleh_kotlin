package top.easyware.submit_planner.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.easyware.submit_planner.ReminderScheduler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubmitPlannerModule {

    @Provides
    @Singleton
    fun provideReminderScheduler(@ApplicationContext context: Context): ReminderScheduler {
        return ReminderScheduler(context = context);
    }
}