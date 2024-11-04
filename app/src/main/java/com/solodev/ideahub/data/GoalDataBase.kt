package com.solodev.ideahub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GoalItem::class], version = 1, exportSchema = false)
abstract class GoalDataBase:RoomDatabase() {

    abstract fun goalItemDao(): GoalItemDao

    companion object {
        @Volatile
        private var Instance: GoalDataBase? = null;

        fun getDataBase(context: Context): GoalDataBase {
            return Instance ?: synchronized(this) {

                Room.databaseBuilder(
                    context,
                    GoalDataBase::class.java,
                    "goal_database"
                ).build().also { Instance = it }
            }
        }
    }
}