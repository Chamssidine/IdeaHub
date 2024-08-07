package com.solodev.ideahub.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Tools {
    companion object {
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
            return  format.format(date)
        }
    }
}