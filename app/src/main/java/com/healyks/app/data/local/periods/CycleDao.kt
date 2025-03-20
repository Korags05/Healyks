package com.healyks.app.data.local.periods

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.healyks.app.data.model.Cycle
import com.healyks.app.data.model.CyclePrediction
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {

    @Query("SELECT * FROM predictions ORDER BY id DESC LIMIT 1")
    fun getLatestPrediction(): Flow<CyclePrediction?>

    @Insert
    suspend fun insert(cycle: Cycle)
    @Insert
    suspend fun insertPrediction(cyclePrediction: CyclePrediction)

    @Query("SELECT * FROM cycle_table ORDER BY startDate DESC")
    fun getAllCycles(): Flow<List<Cycle>>
}