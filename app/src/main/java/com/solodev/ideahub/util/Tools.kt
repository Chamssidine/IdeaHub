package com.solodev.ideahub.util

import java.text.SimpleDateFormat
import java.util.Date

class Tools {
    companion object {
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("MM/dd/yyyy")
            return  format.format(date)
        }
    }
}