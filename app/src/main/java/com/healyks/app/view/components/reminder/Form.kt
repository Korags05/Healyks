package com.healyks.app.view.components.reminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.healyks.app.view.components.core.CustomTextField

@Composable
fun Form(
    time: String,
    onTimeClick: () -> Unit,
    onClick: (String, String) -> Unit  // Removed Boolean parameter
) {
    val name = remember { mutableStateOf("") }
    val dosage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = "name",
            copy = 1f,
            keyboardOptions = KeyboardOptions.Default,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = dosage.value,
            onValueChange = { dosage.value = it },
            label = "dosage",
            copy = 1f,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = time,
            onValueChange = { },
            modifier = Modifier
                .clickable { onTimeClick.invoke() }
                .fillMaxWidth(),
            enabled = false,
            label = { Text("Reminder Time") }
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { onClick.invoke(name.value, dosage.value) }
        ) {
            Text(
                text = "Save"
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}