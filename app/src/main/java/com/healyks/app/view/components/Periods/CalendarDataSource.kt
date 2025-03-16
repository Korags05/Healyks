package com.healyks.app.view.components.Periods

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.YearMonth

class CalendarDataSource {
    var updatedHolidayList = mutableListOf<LocalDate>()
    var periodStartDates = mutableListOf<LocalDate>()
    var periodEndDates = mutableListOf<LocalDate>()
    var nextPeriodDate by mutableStateOf<LocalDate?>(null)
    var ovulationDate by mutableStateOf<LocalDate?>(null)
    var fertileWindow by mutableStateOf<Pair<LocalDate, LocalDate>?>(null)

    // CalendarDataSource.kt
    fun getDates(yearMonth: YearMonth): List<CalendarUiState.Date> {
        return yearMonth.getDayOfMonthStartingFromMonday().map { date ->
            CalendarUiState.Date(
                dayOfMonth = if (date.monthValue == yearMonth.monthValue) {
                    "${date.dayOfMonth}"
                } else {
                    ""
                },
                isHoliday = updatedHolidayList.contains(date),
                date = date,
                isCurrentMonth = date.monthValue == yearMonth.monthValue,
                isPeriodStart = periodStartDates.contains(date),
                isPeriodEnd = periodEndDates.contains(date),
                isNextPeriod = date == nextPeriodDate,
                isOvulation = date == ovulationDate,
                isFertileWindow = fertileWindow?.let { date in it.first..it.second } ?: false
            )
        }
    }
}
