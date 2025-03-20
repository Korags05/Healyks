package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.R
import com.healyks.app.data.model.Reminder
import com.healyks.app.data.util.cancelAlarm
import com.healyks.app.data.util.setUpAlarm
import com.healyks.app.view.components.reminder.Form
import com.healyks.app.view.components.reminder.NotificationPermissionHandler
import com.healyks.app.vm.ReminderViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen(
    navController: NavController,
    reminderViewModel: ReminderViewModel = hiltViewModel()
) {
    val uiState by reminderViewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isTimePickerVisible = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    val format = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val timeInMillis = remember { mutableStateOf(System.currentTimeMillis()) }

    // Wrap everything with the notification permission handler
    NotificationPermissionHandler {
        Scaffold(
            topBar = {
                TopBar(
                    name = "Medication Reminders",
                    onBackClick = { navController.navigateUp() }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        isSheetOpen = true
                        scope.launch { sheetState.show() }
                    },
                    modifier = Modifier.padding(bottom = 128.dp),
                    containerColor = MaterialTheme.colorScheme.onBackground
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Reminder",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
        ) { paddingValues ->

            if (isTimePickerVisible.value) {
                Dialog(onDismissRequest = { isTimePickerVisible.value = false }) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Select Time",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            TimePicker(state = timePickerState)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        isTimePickerVisible.value = false
                                    }
                                ) {
                                    Text("Cancel")
                                }

                                Button(
                                    onClick = {
                                        val calendar = Calendar.getInstance().apply {
                                            set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                            set(Calendar.MINUTE, timePickerState.minute)
                                            set(Calendar.SECOND, 0)
                                            set(Calendar.MILLISECOND, 0)
                                        }
                                        timeInMillis.value = calendar.timeInMillis
                                        isTimePickerVisible.value = false
                                    }
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }
            }

            if (uiState.data.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No reminders found. Add one with the + button.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    items(uiState.data, key = { it.id }) { reminder ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = reminder.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = reminder.dosage,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = format.format(reminder.timeInMillis),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                IconButton(onClick = {
                                    cancelAlarm(context, reminder)
                                    reminderViewModel.delete(reminder)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = "Delete reminder"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (isSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false },
        ) {
            Form(
                time = format.format(timeInMillis.value),
                onTimeClick = { isTimePickerVisible.value = true }
            ) { name, dosage ->
                val reminder = Reminder(
                    name = name,
                    dosage = dosage,
                    timeInMillis = timeInMillis.value,
                    isTaken = false,
                    isRepeat = false  // Always false as we've removed scheduling
                )
                scope.launch {
                    val insertedId = reminderViewModel.insert(reminder)
                    val newReminder = reminder.copy(id = insertedId)
                    setUpAlarm(context, newReminder) // Schedule AFTER getting real ID
                    sheetState.hide()
                    isSheetOpen = false
                }
            }
        }
    }
}