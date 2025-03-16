package com.healyks.app.view.components.Periods

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Oak
import com.healyks.app.vm.CalendarViewModel
import java.time.LocalDate

@Composable
fun Content(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val dates = uiState.dates
    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 6.dp)
    ) {
        var index = 0
        val rows = dates.size/7 + if (dates.size%7!=0) 1 else 0
        repeat(rows) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    ContentItem(
                        date = item,
                        onClickListener = {
                            selectedDate.value = it.date
                            onDateClickListener(it)
                        },
                        isSelected = selectedDate.value == item.date,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
}

// ContentItem.kt
@Composable
fun ContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { onClickListener(date) }
            .size(40.dp)
            .border(
                width = if (isSelected && date.date != LocalDate.now() && date.isCurrentMonth) 1.dp else 0.dp,
                color = if (isSelected && date.date != LocalDate.now() && date.isCurrentMonth) Color.Black else Color.Transparent,
                shape = CircleShape
            )
            .background(
                color = when {
                    date.isPeriodStart || date.isPeriodEnd -> Color.Red.copy(alpha = 0.7f)
                    date.isNextPeriod -> Color.Magenta.copy(alpha = 0.7f)
                    date.isOvulation -> Color.Blue.copy(alpha = 0.7f)
                    date.isFertileWindow -> Color.Green.copy(alpha = 0.5f)
                    date.date == LocalDate.now() -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                    date.isHoliday -> Color.Cyan.copy(alpha = 0.3f)
                    else -> Color.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth,
            style = MaterialTheme.typography.bodyMedium,
            color = when {
                !date.isCurrentMonth -> Color.Gray
                date.date == LocalDate.now() -> MaterialTheme.colorScheme.onBackground.copy(0.7f)
                else -> MaterialTheme.colorScheme.onBackground
            }
        )
    }
}