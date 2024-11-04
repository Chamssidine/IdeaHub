package com.solodev.ideahub.data

import kotlinx.coroutines.flow.Flow

interface DayPlansRepository {
    suspend fun insertDayPlan(dayPlan: DayPlanItem)

    suspend fun deleteDayPlan(dayPlan: DayPlanItem)

    suspend fun updateDayPlan(dayPlan: DayPlanItem)

    fun getAllDayPlans(): Flow<List<DayPlanItem>>

    fun getDayPlanById(id: Long): Flow<DayPlanItem?>
}
