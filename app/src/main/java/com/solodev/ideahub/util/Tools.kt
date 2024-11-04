package com.solodev.ideahub.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.room.TypeConverter
import com.solodev.ideahub.ui.screen.goalScreen.Priority

class Tools {
    companion object {
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            return  format.format(date)
        }
        fun getCurrentDate(): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return format.format(Date())
        }
    }
}


class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return data.split(",").map { it.trim() }
    }
}
