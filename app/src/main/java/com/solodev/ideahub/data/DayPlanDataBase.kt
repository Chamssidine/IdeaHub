package com.solodev.ideahub.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solodev.ideahub.util.Converters

@Database(entities = [DayPlanItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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