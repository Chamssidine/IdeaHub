package com.solodev.ideahub.data
import kotlinx.coroutines.flow.Flow

class OfflineGoalsRepository(private val goalItemDao: GoalItemDao): GoalsRepository {

    override fun getGoalsStream(): Flow<List<GoalItem>>  = goalItemDao.getAllGoalItems()

    override fun getGoalStream(id: Long): Flow<GoalItem?> = goalItemDao.getGoalItem(id)

    override suspend fun updateGoal(goal: GoalItem) = goalItemDao.update(goal)

    override suspend fun deleteGoal(goal: GoalItem) = goalItemDao.delete(goal)

    override suspend fun insertGoal(goal: GoalItem) = goalItemDao.insert(goal)
}