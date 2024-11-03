package com.solodev.ideahub.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val description: String,
    val deadline: String,
    val reminderFrequency: String,
    val creationDate: String,
    var isCompleted: Boolean,
    var delete: Boolean,
)