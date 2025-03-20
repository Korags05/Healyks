package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.data.model.Cycle
import com.healyks.app.ui.theme.Beige
import com.healyks.app.view.components.Periods.Calendar
import com.healyks.app.view.components.Periods.CalendarLegend
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.components.core.DatePickerField
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.CalendarViewModel
import com.healyks.app.vm.CycleViewModel
import java.time.LocalDate
import java.time.ZoneId
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

    // Collect all cycles and predictions from the CycleViewModel
    val allCycles by cycleViewModel.allCycles.collectAsState(initial = emptyList())

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

    Scaffold(topBar = {
        TopBar(
            name = "Shelyks",
            onBackClick = { navController.navigateUp() }
        )
    }) { innerPadding ->

        Surface (
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Calendar View
                Calendar(calendarViewModel, onDayClick = { selectedDate -> })

                // Calendar Legend
                CalendarLegend()

                // Period Save Form
                Column(
                    Modifier.background(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add New Period", color = Beige, style = MaterialTheme.typography.titleMedium)
                    DatePickerField("Start Date", startDate) { startDate = it }
                    DatePickerField("End Date", endDate) { endDate = it }
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
                        }
                    ) {
                        Text("Save Period")
                    }
                }

                CustomButton(
                    onClick = {navController.navigate(HealyksScreens.NextPeriodCalculatorScreen.route)},
                    modifier = Modifier
                        .fillMaxWidth(0.75f),
                    label = "Calculate next period",
                    copy = 0.75f,
                    style = MaterialTheme.typography.titleLarge
                )

                if (showError) {
                    Text(
                        text = "Please fill in all fields.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
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