package com.solodev.ideahub.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayPlan(dayPlan: DayPlanItem)

    @Delete
    suspend fun deleteDayPlan(dayPlan: DayPlanItem)

    @Update
    suspend fun updateDayPlan(dayPlan: DayPlanItem)

    @Query("SELECT * FROM DayPlanItem")
    fun getAllDayPlans(): Flow<List<DayPlanItem>>

    @Query("SELECT * FROM DayPlanItem WHERE id = :id")
    fun getDayPlanById(id: Long): Flow<DayPlanItem?>



}