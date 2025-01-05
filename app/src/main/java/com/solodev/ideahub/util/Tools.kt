package com.solodev.ideahub.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.room.TypeConverter
import com.solodev.ideahub.model.UserProfile
import com.solodev.ideahub.model.personalStatistics
import com.solodev.ideahub.model.userSettings
import com.solodev.ideahub.ui.screen.dayplanScreen.Priority
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class Tools {
    companion object {
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            return  format.format(date)
        }
        fun getCurrentDate(): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            Log.d("Tools", "min = ${getMinuteFromTimestamp(Date().time)}")
            return format.format(Date())
        }

        private fun getMinuteFromTimestamp(timestamp: Long): Int {
            val instant = Instant.ofEpochMilli(timestamp)
            val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            return localDateTime.minute
        }
    }
}

class UserHandler {
    companion object {
        fun getCurrentuser(): UserProfile {


            val userProfile = UserProfile(
                id = Locale.getDefault().toString(),
                name = "CHRISTOPHER",
                biography = "Biographie",
                publicationTime = 20,
                profileImage = "https://example.com/path/to/profile_image.png",
                personalStatistics = personalStatistics,
                userSettings = userSettings
            )

            return  userProfile
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
