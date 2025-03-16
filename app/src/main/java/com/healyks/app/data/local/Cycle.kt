package com.healyks.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "cycle_table")
data class Cycle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startDate: Date, // Room will use the TypeConverter for this
    val endDate: Date,   // Room will use the TypeConverter for this
    val cycleLength: Int // Length of the cycle in days
)