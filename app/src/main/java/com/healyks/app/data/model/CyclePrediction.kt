package com.healyks.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "predictions")
data class CyclePrediction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nextPeriodDate: Date,  // Now supported via converters
    val ovulationDate: Date,
    val fertileWindowStart: Date,
    val fertileWindowEnd: Date
)