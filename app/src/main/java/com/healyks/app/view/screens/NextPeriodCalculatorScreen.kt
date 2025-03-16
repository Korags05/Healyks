package com.healyks.app.view.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.vm.CycleViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import com.healyks.app.data.local.CyclePrediction
import com.healyks.app.vm.CalendarViewModel
import java.time.format.DateTimeFormatter

@Composable
fun NextPeriodCalculatorScreen(
    navController: NavController,
    viewModel: CycleViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    var lastPeriodDate by remember { mutableStateOf("") }
    var cycleLength by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showResults by remember { mutableStateOf(false) }

    // Observe calculation results
    val nextPeriodDate by viewModel.nextPeriodDate.collectAsState()
    val ovulationDate by viewModel.ovulationDate.collectAsState()
    val fertileWindow by viewModel.fertileWindow.collectAsState()

    val validInput = remember(lastPeriodDate, cycleLength) {
        val isDateValid = runCatching { LocalDate.parse(lastPeriodDate) }.isSuccess
        val isCycleValid = cycleLength.toIntOrNull()?.let { it in 20..45 } ?: false
        isDateValid && isCycleValid
    }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMMM dd, yyyy") }

    BackHandler {
        navController.popBackStack()
    }

    LaunchedEffect(Unit) {
        calendarViewModel.observePredictions(viewModel)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Next Period Calculator",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = lastPeriodDate,
                    onValueChange = { lastPeriodDate = it },
                    label = { Text("Last Period Start Date (YYYY-MM-DD)") },
                    isError = showError && lastPeriodDate.isBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = cycleLength,
                    onValueChange = { cycleLength = it },
                    label = { Text("Cycle Length (days)") },
                    isError = showError && cycleLength.isBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val parsedDate = try {
                            LocalDate.parse(lastPeriodDate)
                        } catch (e: Exception) {
                            showError = true
                            return@Button
                        }
                        val cycleLengthInt = cycleLength.toIntOrNull() ?: run {
                            showError = true
                            return@Button
                        }
                        if (cycleLengthInt !in 20..45) {
                            showError = true
                            return@Button
                        }

                        viewModel.calculatePredictions(parsedDate, cycleLengthInt)
                        viewModel.insertPrediction(
                            CyclePrediction(
                                nextPeriodDate = parsedDate.plusDays(cycleLengthInt.toLong()).toDate(),
                                ovulationDate = parsedDate.plusDays(cycleLengthInt.toLong() - 14).toDate(),
                                fertileWindowStart = parsedDate.plusDays(cycleLengthInt.toLong() - 19).toDate(),
                                fertileWindowEnd = parsedDate.plusDays(cycleLengthInt.toLong() - 13).toDate()
                            )
                        )
                        showError = false
                        showResults = true
                    }
                ) {
                    Text("Calculate & Save")
                }

                if (showError) {
                    Text(
                        text = "Please enter a valid date (YYYY-MM-DD) and cycle length.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        // Show results only when showResults is true
        if (showResults) {
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Your Cycle Predictions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    nextPeriodDate?.let {
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("Next Period:", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.width(8.dp))
                            Text(it.format(dateFormatter), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    ovulationDate?.let {
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("Ovulation Date:", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.width(8.dp))
                            Text(it.format(dateFormatter), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    fertileWindow?.let {
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("Fertile Window:", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "${it.first.format(dateFormatter)} to ${it.second.format(dateFormatter)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Note: These predictions are based on a typical menstrual cycle and may vary.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Period Tracker")
            }
        }
    }
}
