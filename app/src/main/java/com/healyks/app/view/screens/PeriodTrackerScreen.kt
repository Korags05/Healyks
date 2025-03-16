package com.healyks.app.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.data.local.Cycle
import com.healyks.app.view.components.Periods.Calendar
import com.healyks.app.view.components.Periods.CalendarLegend
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.CalendarViewModel
import com.healyks.app.vm.CycleViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


@Composable
fun PeriodTrackerScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    cycleViewModel: CycleViewModel = hiltViewModel()
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var showError by remember { mutableStateOf(false) }
    var showPredictionsInfo by remember { mutableStateOf(false) }

    // Collect all cycles and predictions from the CycleViewModel
    val allCycles by cycleViewModel.allCycles.collectAsState(initial = emptyList())
    val nextPeriodDate by cycleViewModel.nextPeriodDate.collectAsState()
    val ovulationDate by cycleViewModel.ovulationDate.collectAsState()
    val fertileWindow by cycleViewModel.fertileWindow.collectAsState()

    // Format for displaying dates
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    LaunchedEffect(cycleViewModel.nextPeriodDate, cycleViewModel.ovulationDate, cycleViewModel.fertileWindow) {
        calendarViewModel.updatePredictions(
            cycleViewModel.nextPeriodDate.value,
            cycleViewModel.ovulationDate.value,
            cycleViewModel.fertileWindow.value
        )
    }

    // Update the CalendarViewModel with period dates and predictions
    LaunchedEffect(allCycles, cycleViewModel.nextPeriodDate, cycleViewModel.ovulationDate, cycleViewModel.fertileWindow) {
        allCycles.forEach { cycle ->
            calendarViewModel.updatePeriodDates(
                cycle.startDate.toLocalDate(),
                cycle.endDate.toLocalDate()
            )
        }

        // Force refresh predictions
        calendarViewModel.updatePredictions(
            cycleViewModel.nextPeriodDate.value,
            cycleViewModel.ovulationDate.value,
            cycleViewModel.fertileWindow.value
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Calendar View
        Calendar(calendarViewModel, onDayClick = { selectedDate -> })


        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Legend
        CalendarLegend()

        Spacer(modifier = Modifier.height(16.dp))

        // Period Save Form
        Text("Add New Period", style = MaterialTheme.typography.titleMedium)
        DatePickerField("Start Date", startDate) { startDate = it }
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField("End Date", endDate) { endDate = it }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (startDate == null || endDate == null) {
                        showError = true
                    } else {
                        // Save the new cycle to the database
                        cycleViewModel.insertCycle(
                            Cycle(
                                startDate = startDate!!.toDate(),
                                endDate = endDate!!.toDate(),
                                cycleLength = calculateCycleLength(startDate!!, endDate!!)
                            )
                        )

                        // If this is the first period, automatically calculate predictions
                        if (allCycles.isEmpty()) {
                            val avgCycleLength = 28 // Default average cycle length
                            cycleViewModel.calculatePredictions(startDate!!, avgCycleLength)
                        }

                        startDate = null
                        endDate = null
                        showError = false
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Period")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Navigation to Next Period Calculator
            Button(
                onClick = { navController.navigate(HealyksScreens.NextPeriodCalculatorScreen.route) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Calculate Next Period")
            }
        }

        if (showError) {
            Text(
                text = "Please fill in all fields.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Helper function to calculate cycle length
fun calculateCycleLength(startDate: LocalDate, endDate: LocalDate): Int {
    return ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1
}

// Extension functions to convert between LocalDate and Date
fun LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit // Change to accept LocalDate directly
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Column {
        OutlinedTextField(
            value = selectedDate?.let { dateFormatter.format(it.toDate()) } ?: "",
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            )
        )

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                                onDateSelected(date)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    title = { Text("Select $label") }
                )
            }
        }
    }
}