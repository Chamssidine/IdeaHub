package com.solodev.ideahub.data
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {

    //fun insertGoal(goal: Goal)
    fun getGoalsStream(): Flow<List<GoalItem>>

    fun getGoalStream(id: Long): Flow<GoalItem?>

    //fun updateGoal(goal: Goal)
    suspend fun updateGoal(goal: GoalItem)

    suspend fun deleteGoal(goal: GoalItem)

    suspend fun insertGoal(goal: GoalItem)

}