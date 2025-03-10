package com.healyks.app.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.healyks.app.view.components.core.CustomDropdown
import com.healyks.app.view.components.core.CustomTextField
import com.healyks.app.view.components.core.TagInputField

@Composable
fun postUserBodyScreen() {
    val age = remember { mutableStateOf("") }
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
    val allergiesOptions = listOf("Pollen", "Peanuts", "Dust", "Other")
    val diseasesOptions = listOf("Diabetes", "Hypertension", "Asthma", "Other")
    val bloodGroup = remember { mutableStateOf("") }
    val selectedAllergies = remember { mutableStateListOf<String>() }
    val selectedDiseases = remember { mutableStateListOf<String>() }
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val medications = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val smoking = remember { mutableStateOf("") }
    val alcohol = remember { mutableStateOf("") }
    val exercise = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Consistent spacing between items
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Fill your details",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Medium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Age:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
                Spacer(modifier = Modifier.width(8.dp))

                CustomTextField(
                value = age.value,
                onValueChange = { age.value = it },
                label = "eg: 19",
                copy = 0.6f,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Blood Group:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomDropdown(
                label = "Blood Group",
                options = bloodGroups,
                selectedOption = bloodGroup.value,
                onOptionSelected = { bloodGroup.value = it }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Height:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            CustomTextField(
                value = height.value,
                onValueChange = { height.value = it },
                label = "in cm",
                copy = 0.6f,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Weight",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            CustomTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = "in kgs",
                copy = 0.6f,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }

        Column {
            Text(
                text = "Allergies:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            TagInputField(
                label = "Select from dropdown",
                options = allergiesOptions,
                selectedTags = selectedAllergies,
                onTagAdded = { if (!selectedAllergies.contains(it)) selectedAllergies.add(it) },
                onTagRemoved = { selectedAllergies.remove(it) }
            )
        }

        Column {
            Text(
                text = "Medications:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            CustomTextField(
                value = medications.value,
                onValueChange = { medications.value = it },
                label = "eg: Paracetamol, Amlodac-50",
                copy = 1f,
                keyboardOptions = KeyboardOptions.Default,
            )
        }

        Column {
            Text(
                text = "Chronic diseases:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            TagInputField(
                label = "Select from dropdown",
                options = diseasesOptions,
                selectedTags = selectedDiseases,
                onTagAdded = { if (!selectedDiseases.contains(it)) selectedDiseases.add(it) },
                onTagRemoved = { selectedDiseases.remove(it) }
            )
        }

        Column {
            Text(
                text = "Gender:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            CustomTextField(
                value = gender.value,
                onValueChange = { gender.value = it },
                label = "eg: Male, Female, Others",
                copy = 1f,
                keyboardOptions = KeyboardOptions.Default,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Smoking:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomTextField(
                value = smoking.value,
                onValueChange = { smoking.value = it },
                label = "eg: Yes/No",
                copy = 0.6f,
                keyboardOptions = KeyboardOptions.Default,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Alcohol:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.4f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomTextField(
                value = alcohol.value,
                onValueChange = { alcohol.value = it },
                label = "eg: Yes/No",
                copy = 0.6f,
                keyboardOptions = KeyboardOptions.Default,
            )
        }


        Text(
            text = "Exercise:",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
        CustomTextField(
            value = exercise.value,
            onValueChange = { exercise.value = it },
            label = "eg: Daily, Weekly",
            copy = 1f,
            keyboardOptions = KeyboardOptions.Default,
        )

    }
}

@Preview(name = "Small Phone", device = Devices.PIXEL)
@Preview(name = "Medium Phone", device = Devices.DEFAULT)
@Preview(name = "Large Phone", device = Devices.PIXEL_XL)
@Composable
private fun preview() {
    postUserBodyScreen()
}