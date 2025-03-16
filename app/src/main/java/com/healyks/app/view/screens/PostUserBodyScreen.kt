package com.healyks.app.view.screens

import SelectingCards
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.R
import com.healyks.app.data.model.Lifestyle
import com.healyks.app.data.model.UserDetails
import com.healyks.app.state.UiState
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Oak
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.components.core.CustomDropdown
import com.healyks.app.view.components.core.CustomTextField
import com.healyks.app.view.components.core.TagInputField
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun PostUserBodyScreen(
    isEditMode: Boolean = false,
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userEmail = currentUser?.email ?: "No Email"

    val coroutineScope = rememberCoroutineScope()
    val age = remember { mutableStateOf("") }
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
    val allergiesOptions = listOf("Pollen", "Peanuts", "Dust", "Other")
    val diseasesOptions = listOf("Diabetes", "Hypertension", "Asthma", "Other")
    val bloodGroup = rememberSaveable { mutableStateOf("") }
    val selectedAllergies = rememberSaveable(
        saver = listSaver(
            save = { ArrayList(it) }, // Convert to ArrayList which can be saved in a Bundle
            restore = { it.toMutableStateList() } // Convert back to SnapshotStateList
        )
    ) { mutableStateListOf<String>() }

    val selectedDiseases = rememberSaveable(
        saver = listSaver(
            save = { ArrayList(it) },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf<String>() }
    val height = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val medications = remember { mutableStateOf("") }
    val selectedGender = rememberSaveable { mutableStateOf<String?>(null) }

    val lifestyleOptions = listOf("Smoking", "Drinking", "Exercise")
    val selectedLifestyles = rememberSaveable(
        saver = listSaver(
            save = { ArrayList(it) },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf<String>() }

    val exerciseFrequencyOptions = listOf("Daily", "Weekly", "Monthly")
    val exerciseFrequency = rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()


    val postUserState = userViewModel.postUserState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(postUserState) {
        when (postUserState) {
            is UiState.Success -> {
                Toast.makeText(context, "User details posted successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(HealyksScreens.DashboardScreen.route) {
                    popUpTo(HealyksScreens.PostUserBodyScreen.route) {
                        saveState = true
                        inclusive = true
                    }
                }
                userViewModel.resetPostUserState()
            }

            is UiState.Failed -> {
                Toast.makeText(context, postUserState.message, Toast.LENGTH_SHORT).show()
                userViewModel.resetPostUserState()
            }

            else -> {}
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Fill your details",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displaySmall,
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
                copy = 0.6f,
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
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SelectingCards(
                    label = "Male",
                    iconRes = R.drawable.male,
                    isSelected = selectedGender.value == "Male",
                    onClick = { selectedGender.value = "Male" }
                )
                SelectingCards(
                    label = "Female",
                    iconRes = R.drawable.female,
                    isSelected = selectedGender.value == "Female",
                    onClick = { selectedGender.value = "Female" }
                )
                SelectingCards(
                    label = "Other",
                    iconRes = R.drawable.other,
                    isSelected = selectedGender.value == "Other",
                    onClick = { selectedGender.value = "Other" }
                )
            }
        }

        Column {
            Text(
                text = "Lifestyle:",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                lifestyleOptions.forEach { option ->
                    SelectingCards(
                        label = option,
                        iconRes = when (option) {
                            "Smoking" -> R.drawable.cigar
                            "Drinking" -> R.drawable.beer
                            "Exercise" -> R.drawable.physicalactivity
                            else -> R.drawable.g
                        },
                        isSelected = selectedLifestyles.contains(option),
                        onClick = {
                            if (selectedLifestyles.contains(option)) {
                                selectedLifestyles.remove(option)
                                if (option == "Exercise") exerciseFrequency.value = "" // Reset frequency when deselecting Exercise
                            } else {
                                selectedLifestyles.add(option)
                            }
                        }
                    )
                }
            }

            if (selectedLifestyles.contains("Exercise")) {
                Spacer(modifier = Modifier.height(8.dp))
                CustomDropdown(
                    copy = 1f,
                    label = "Exercise Frequency",
                    options = exerciseFrequencyOptions,
                    selectedOption = exerciseFrequency.value,
                    onOptionSelected = { exerciseFrequency.value = it }
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(2.dp, Coffee),
                onClick = {
                    // Navigate to DashboardScreen
                    navController.navigate(HealyksScreens.DashboardScreen.route) {
                        popUpTo(HealyksScreens.PostUserBodyScreen.route) {
                            inclusive = true // Remove PostUserBodyScreen from the backstack
                        }
                        launchSingleTop = true // Avoid duplicate DashboardScreen instances
                    }
                }
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(6.dp),
                    text = "Skip",
                    color = Beige,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            CustomButton(
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                onClick = {
                    // Validate input fields
                    when {
                        age.value.isEmpty() -> Toast.makeText(
                            context,
                            "Please enter your age",
                            Toast.LENGTH_SHORT
                        ).show()

                        bloodGroup.value.isEmpty() -> Toast.makeText(
                            context,
                            "Please select your blood group",
                            Toast.LENGTH_SHORT
                        ).show()

                        height.value.isEmpty() -> Toast.makeText(
                            context,
                            "Please enter your height",
                            Toast.LENGTH_SHORT
                        ).show()

                        weight.value.isEmpty() -> Toast.makeText(
                            context,
                            "Please enter your weight",
                            Toast.LENGTH_SHORT
                        ).show()

                        selectedGender.value == null -> Toast.makeText(
                            context,
                            "Please select your gender",
                            Toast.LENGTH_SHORT
                        ).show()

                        else -> {
                            // Prepare the UserDetails object
                            val userDetails = UserDetails(
                                age = age.value.toInt(),
                                bloodGroup = bloodGroup.value,
                                height = height.value.toDouble(),
                                weight = weight.value.toDouble(),
                                allergies = selectedAllergies.toList(),
                                chronicDiseases = selectedDiseases.toList(),
                                medications = medications.value.split(",").map { it.trim() },
                                gender = selectedGender.value!!,
                                lifestyle = Lifestyle(
                                    alcohol = selectedLifestyles.contains("Drinking"),
                                    smoking = selectedLifestyles.contains("Smoking"),
                                    physicalActivity = if (selectedLifestyles.contains("Exercise")) {
                                        exerciseFrequency.value
                                    } else {
                                        null
                                    }
                                ),
                                email = userEmail
                            )

                            // Call the ViewModel to post user details
                            coroutineScope.launch {
                                userViewModel.postUser(userDetails)
                                // Set user details filled status to true
                                userViewModel.setUserDetailsFilled(true)
                            }
                        }
                    }
                },
                label = "Save",
                copy = 0.6f,
                weight = FontWeight.SemiBold
            )
        }
    }
}
