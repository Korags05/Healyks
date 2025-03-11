package com.healyks.app.view.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagInputField(
    label: String,
    options: List<String>,
    selectedTags: List<String>,
    onTagAdded: (String) -> Unit,
    onTagRemoved: (String) -> Unit
) {
    // Use rememberSaveable instead of remember to preserve state during configuration changes
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showOtherField by rememberSaveable { mutableStateOf(false) }
    var otherInput by rememberSaveable { mutableStateOf("") }

    Column {
        // Display selected tags
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedTags.size) { index ->
                val tag = selectedTags[index]
                Chip(tag = tag, onRemove = { onTagRemoved(tag) })
            }
        }

        // Dropdown trigger
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            enabled = false,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledIndicatorColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                cursorColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        // Dropdown menu
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.distinct().forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        if (option == "Other") {
                            showOtherField = true
                        } else {
                            onTagAdded(option)
                        }
                        expanded = false
                    }
                )
            }
            if (!options.contains("Other")) {
                DropdownMenuItem(
                    text = { Text("Other") },
                    onClick = {
                        showOtherField = true
                        expanded = false
                    }
                )
            }
        }

        // "Other" input field
        if (showOtherField) {
            OutlinedTextField(
                value = otherInput,
                onValueChange = { otherInput = it },
                label = { Text("Specify other") },
                trailingIcon = {
                    IconButton(onClick = {
                        if (otherInput.isNotBlank()) {
                            onTagAdded(otherInput)
                            otherInput = ""
                            showOtherField = false
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Confirm")
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(0.75f),
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Chip(tag: String, onRemove: () -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = tag, color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove tag",
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
        }
    }
}