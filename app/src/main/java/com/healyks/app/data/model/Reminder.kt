package com.healyks.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")  // Explicit table name
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val dosage: String,
    val timeInMillis: Long,
    val isTaken: Boolean = false,
    val isRepeat: Boolean = false
)