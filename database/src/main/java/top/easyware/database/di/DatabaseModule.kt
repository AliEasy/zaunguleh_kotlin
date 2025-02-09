package top.easyware.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import top.easyware.database.data_source.PlannerDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    fun providePlannerDatabase(app: Application) : PlannerDatabase {
        return Room.databaseBuilder(
            app,
            PlannerDatabase::class.java,
            PlannerDatabase.DATABASE_NAME
        ).build()
    }
}