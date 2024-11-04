package com.solodev.ideahub.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class DayPlanDataBase: RoomDatabase()    {

    abstract fun dayPlanDao(): DayPlanDao

    companion object {
        @Volatile
        private var Instance: DayPlanDataBase? = null

        fun getDataBase(context: Context): DayPlanDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DayPlanDataBase::class.java,
                    "day_plan_database"
                ).build().also { Instance = it }
            }
        }
    }
}