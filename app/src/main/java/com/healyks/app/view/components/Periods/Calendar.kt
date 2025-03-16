package com.healyks.app.view.components.Periods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.healyks.app.vm.CalendarViewModel
import java.time.LocalDate

@Composable
fun Calendar(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDayClick: (LocalDate) -> Unit
) {
    val date = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    Surface(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        color = Color.Transparent
    ) {
        Column{
            Header(
                onPreviousMonthButtonClicked = { prevMonth ->
                    viewModel.toPreviousMonth(prevMonth)
                },
                onNextMonthButtonClicked = { nextMonth ->
                    viewModel.toNextMonth(nextMonth)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            CalendarDays()
            Spacer(modifier = Modifier.height(16.dp))
            Content(
                onDateClickListener = { selectedDate ->
                    date.value = selectedDate.date
                    selectedDate.date?.let{localDate ->
                        onDayClick(localDate)
                    }
                }
            )
        }
    }
}