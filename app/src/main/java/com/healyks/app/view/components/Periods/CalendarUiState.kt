package com.healyks.app.view.components.Periods

import java.time.LocalDate
import java.time.YearMonth

data class CalendarUiState(
    val yearMonth: YearMonth,
    val dates: List<Date>
) {
    companion object {
        val Init = CalendarUiState(
            yearMonth = YearMonth.now(),
            dates = emptyList()
        )
    }

    data class Date(
        val dayOfMonth: String,
        val isHoliday: Boolean,
        val date: LocalDate,  // Changed to non-null
        val isCurrentMonth: Boolean,
        val isPeriodStart: Boolean = false,
        val isPeriodEnd: Boolean = false,
        val isFertileWindow: Boolean = false,
        val isOvulation: Boolean = false,
        val isNextPeriod: Boolean = false,
    ) {
        companion object {
            val Empty = Date("", false, LocalDate.MIN, false)
        }
    }
}

