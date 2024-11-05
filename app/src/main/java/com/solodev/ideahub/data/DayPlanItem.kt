package com.solodev.ideahub.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solodev.ideahub.ui.screen.dayplanScreen.Priority

@Entity(tableName = "dayPlanItem")
data class DayPlanItem (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val creationDate: String = "",
    val deadline: String = "",
    val priority: Priority = Priority.NONE,
    val tags: List<String> = emptyList(),
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    var delete: Boolean = false,
)