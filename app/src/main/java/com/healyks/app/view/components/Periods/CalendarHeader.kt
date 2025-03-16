package com.healyks.app.view.components.Periods

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healyks.app.vm.CalendarViewModel
import java.time.YearMonth
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Oak
import java.time.format.DateTimeFormatter

@Composable
fun Header(
    onPreviousMonthButtonClicked: (YearMonth) -> Unit = {},
    onNextMonthButtonClicked: (YearMonth) -> Unit = {},
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val calendarUiState = viewModel.uiState.collectAsState().value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Previous Month",
            modifier = Modifier
                .clickable {
                    val prevMonth = calendarUiState.yearMonth.minusMonths(1)
                    onPreviousMonthButtonClicked(prevMonth)
                }
                .padding(start = 16.dp)
        )

        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        Text(
            text = calendarUiState.yearMonth.format(formatter),
            fontSize = 22.sp,
            color = Coffee,
            style = MaterialTheme.typography.titleMedium
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next Month",
            modifier = Modifier
                .clickable {
                    val nextMonth = calendarUiState.yearMonth.plusMonths(1)
                    onNextMonthButtonClicked(nextMonth)
                }
                .padding(end = 16.dp)
        )
    }
}