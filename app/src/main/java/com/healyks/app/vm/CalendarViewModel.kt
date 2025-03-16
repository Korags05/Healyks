package com.healyks.app.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healyks.app.view.components.Periods.CalendarDataSource
import com.healyks.app.view.components.Periods.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewModel : ViewModel() {

    private val dataSource = CalendarDataSource()
    private val _uiState = MutableStateFlow(CalendarUiState.Init)
    val uiState: StateFlow<CalendarUiState> = _uiState

    fun forceRefresh() {
        updateMonth(_uiState.value.yearMonth)
    }

    init {
        updateMonth(YearMonth.now())
    }

    fun observePredictions(viewModel: CycleViewModel) {
        viewModelScope.launch {
            combine(
                viewModel.nextPeriodDate,
                viewModel.ovulationDate,
                viewModel.fertileWindow
            ) { next, ovu, fertile ->
                Triple(next, ovu, fertile)
            }.collect { (next, ovu, fertile) ->
                updatePredictions(next, ovu, fertile)
                forceRefresh()
            }
        }
    }


    fun updateMonth(yearMonth: YearMonth) {
        _uiState.update {
            it.copy(
                yearMonth = yearMonth,
                dates = dataSource.getDates(yearMonth)
            )
        }
    }

    fun updatePeriodDates(startDate: LocalDate, endDate: LocalDate) {
        dataSource.periodStartDates.add(startDate)
        dataSource.periodEndDates.add(endDate)
        updateMonth(_uiState.value.yearMonth)
    }

    fun updatePredictions(
        nextPeriod: LocalDate?,
        ovulation: LocalDate?,
        fertileWindow: Pair<LocalDate, LocalDate>?
    ) {
        Log.d("CalendarViewModel", "Updating predictions in Calendar: $nextPeriod, $ovulation, $fertileWindow")

        dataSource.nextPeriodDate = nextPeriod
        dataSource.ovulationDate = ovulation
        dataSource.fertileWindow = fertileWindow

        _uiState.update { currentState ->
            currentState.copy(
                dates = dataSource.getDates(currentState.yearMonth)
            )
        }

        forceRefresh() // Ensures recomposition
    }



    fun toPreviousMonth(prevMonth: YearMonth) {
        updateMonth(prevMonth)
    }

    fun toNextMonth(nextMonth: YearMonth) {
        updateMonth(nextMonth)
    }
}