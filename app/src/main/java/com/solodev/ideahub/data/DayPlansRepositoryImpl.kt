package com.solodev.ideahub.data

import kotlinx.coroutines.flow.Flow

class DayPlansRepositoryImpl(
    private val dayPlanDao: DayPlanDao
) : DayPlansRepository {

    // Function to insert a DayPlanItem into the database
    override suspend fun insertDayPlan(dayPlan: DayPlanItem) {
        dayPlanDao.insertDayPlan(dayPlan)
    }

    // Function to delete a DayPlanItem from the database
    override suspend fun deleteDayPlan(dayPlan: DayPlanItem) {
        dayPlanDao.deleteDayPlan(dayPlan)
    }

    // Function to update a DayPlanItem in the database
    override suspend fun updateDayPlan(dayPlan: DayPlanItem) {
        dayPlanDao.updateDayPlan(dayPlan)
    }

    // Function to get all DayPlanItems as a Flow
    override fun getAllDayPlans(): Flow<List<DayPlanItem>> {
        return dayPlanDao.getAllDayPlans()
    }

    // Function to get a specific DayPlanItem by its ID
    override fun getDayPlanById(id: Long): Flow<DayPlanItem?> {
        return dayPlanDao.getDayPlanById(id)
    }
}
