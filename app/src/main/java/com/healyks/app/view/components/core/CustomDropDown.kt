package com.healyks.app.view.components.core

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@Composable
fun CustomDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    copy: Float,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Box(modifier = Modifier
            .fillMaxWidth(copy)
            .clickable { expanded = true }
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, MaterialTheme.shapes.medium)
        ) {
            Text(
                maxLines = 1,
                text = selectedOption.ifEmpty { "Select $label" },
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) }, // <- Changed here
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
