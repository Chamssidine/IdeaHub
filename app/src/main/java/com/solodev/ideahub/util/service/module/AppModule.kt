package com.solodev.ideahub.util.service.module

import android.content.Context
import com.solodev.ideahub.data.GoalDataBase
import com.solodev.ideahub.data.GoalItemDao
import com.solodev.ideahub.data.GoalsRepository
import com.solodev.ideahub.data.OfflineGoalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GoalDataBase {
        return GoalDataBase.getDataBase(context)
    }

    @Provides
    fun provideGoalItemDao(database: GoalDataBase): GoalItemDao {
        return database.goalItemDao()
    }

    @Provides
    @Singleton
    fun provideGoalsRepository(goalItemDao: GoalItemDao): GoalsRepository {
        return OfflineGoalsRepository(goalItemDao)
    }
}
