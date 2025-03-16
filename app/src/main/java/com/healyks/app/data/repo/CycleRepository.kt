package com.healyks.app.data.repo

import com.healyks.app.data.local.Cycle
import com.healyks.app.data.local.CycleDao
import com.healyks.app.data.local.CyclePrediction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CycleRepository @Inject constructor(
    private val cycleDao: CycleDao
) {
    val allCycles: Flow<List<Cycle>> = cycleDao.getAllCycles()

    suspend fun insert(cycle: Cycle) {
        cycleDao.insert(cycle)
    }
    val latestPrediction: Flow<CyclePrediction?> = cycleDao.getLatestPrediction()

    suspend fun insertPrediction(cyclePrediction: CyclePrediction) {
        cycleDao.insertPrediction(cyclePrediction)
    }
}