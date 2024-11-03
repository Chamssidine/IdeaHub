package com.solodev.ideahub.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(goalItem: GoalItem)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(goalItem: GoalItem)

    @Delete
    suspend fun delete(goalItem: GoalItem)

    @Query("SELECT * from goalItem WHERE id = :id")
    fun getGoalItem(id: Long): Flow<GoalItem>

    @Query("SELECT * from goalItem ORDER BY title ASC")
    fun getAllGoalItems(): Flow<List<GoalItem>>

}