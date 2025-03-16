package com.healyks.app.vm

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healyks.app.data.local.Cycle
import com.healyks.app.data.local.CyclePrediction
import com.healyks.app.data.repo.CycleRepository
import com.healyks.app.view.screens.toDate
import com.healyks.app.view.screens.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CycleViewModel @Inject constructor(
    private val repository: CycleRepository
) : ViewModel() {

    val allCycles: Flow<List<Cycle>> = repository.allCycles

    private val _nextPeriodDate = MutableStateFlow<LocalDate?>(null)
    val nextPeriodDate: StateFlow<LocalDate?> = _nextPeriodDate

    private val _ovulationDate = MutableStateFlow<LocalDate?>(null)
    val ovulationDate: StateFlow<LocalDate?> = _ovulationDate

    private val _fertileWindow = MutableStateFlow<Pair<LocalDate, LocalDate>?>(null)
    val fertileWindow: StateFlow<Pair<LocalDate, LocalDate>?> = _fertileWindow

    fun insertCycle(cycle: Cycle) = viewModelScope.launch {
        repository.insert(cycle)
    }
    fun insertPrediction(cyclePrediction: CyclePrediction) = viewModelScope.launch {
        repository.insertPrediction(cyclePrediction)
    }

    fun calculatePredictions(lastPeriodStartDate: LocalDate, averageCycleLength: Int) {
        viewModelScope.launch {
            try {
                val nextPeriod = lastPeriodStartDate.plusDays(averageCycleLength.toLong())
                val ovulation = nextPeriod.minusDays(14)
                val fertile = Pair(ovulation.minusDays(5), ovulation.plusDays(1))

                _nextPeriodDate.value = nextPeriod
                _ovulationDate.value = ovulation
                _fertileWindow.value = fertile

                Log.d("CycleViewModel", "Predictions updated: $nextPeriod, $ovulation, $fertile")

            } catch (e: Exception) {
                Log.e("CycleViewModel", "Prediction error", e)
            }
        }
    }

    init {
        loadSavedPredictions()
    }

    private fun loadSavedPredictions() {
        viewModelScope.launch {
            repository.latestPrediction.collect { prediction ->
                prediction?.let {
                    _nextPeriodDate.value = it.nextPeriodDate.toLocalDate()
                    _ovulationDate.value = it.ovulationDate.toLocalDate()
                    _fertileWindow.value = Pair(
                        it.fertileWindowStart.toLocalDate(),
                        it.fertileWindowEnd.toLocalDate()
                    )
                }
            }
        }
    }


}