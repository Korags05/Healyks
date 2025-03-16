package com.healyks.app.view.screens

import TopBar
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.R
import com.healyks.app.data.model.UserDetails
import com.healyks.app.state.UiState
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.view.components.FirstAid.ExpandableCard
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    onLogOutClick: () -> Unit = {},
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val loading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val noInternet by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val noData by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val userName = currentUser?.displayName ?: "KIIT Student"
    val userEmail = currentUser?.email ?: "No Email"
    val image = currentUser?.photoUrl?.toString() ?: "drawable://profile"
    val phone = currentUser?.phoneNumber?.toString() ?: "9999999999"

    // Fetch user details
    val getUserState = userViewModel.getUserState.collectAsState().value

    // Fetch user details when the screen is launched
    LaunchedEffect(Unit) {
        userViewModel.getUser()
    }

    Scaffold(topBar = {
        TopBar(
            name = "Profile",
            onBackClick = { navController.navigateUp() }
        )
    }) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = CircleShape,
                        border = BorderStroke(2.dp, Coffee)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(image),
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Display user details based on the state
                when (getUserState) {
                    is UiState.Loading -> {
                        LottieAnimation(
                            modifier = Modifier.size(180.dp),
                            composition = loading,
                            iterations = LottieConstants.IterateForever,
                            contentScale = ContentScale.Fit
                        )
                    }
                    is UiState.Success -> {
                        val userDetails = getUserState.data.data
                        if (userDetails != null) {
                            Surface(
                                modifier = Modifier.padding(8.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(2.dp, Coffee)
                            ) {
                                ProfileDetails(userDetails)
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CustomButton(
                                    onClick = {
                                        // Navigate to PostUserBodyScreen in edit mode
                                        navController.navigate(HealyksScreens.PostUserBodyScreen.route) {
                                            // Clear the backstack up to ProfileScreen
                                            popUpTo(HealyksScreens.ProfileScreen.route) {
                                                saveState = true // Save the state of ProfileScreen
                                            }
                                            launchSingleTop = true // Avoid duplicate PostUserBodyScreen instances
                                            restoreState = true // Restore the state of PostUserBodyScreen
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 38.dp)
                                        .fillMaxWidth(),
                                    label = "Edit Profile",
                                    copy = 0.75f
                                )
                                CustomButton(
                                    onClick = {
                                        try {
                                            onLogOutClick()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 38.dp)
                                        .fillMaxWidth(),
                                    label = "Logout",
                                    copy = 0.75f
                                )
                                CustomButton(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            putExtra(Intent.EXTRA_EMAIL, arrayOf("healyks.team@gmail.com"))
                                            putExtra(Intent.EXTRA_SUBJECT, "Healyks App Feedback")
                                        }
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 4.dp, horizontal = 38.dp)
                                        .fillMaxWidth(),
                                    label = "Feedback",
                                    copy = 0.75f
                                )
                            }
                        } else {
                            // Show a message if no user details are found
                            LottieAnimation(
                                modifier = Modifier.size(180.dp),
                                composition = noData,
                                iterations = LottieConstants.IterateForever,
                                contentScale = ContentScale.Fit
                            )
                            // Add both Fill Details and Logout buttons
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 38.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CustomButton(
                                    onClick = {
                                        // Navigate to PostUserBodyScreen to fill details
                                        navController.navigate(HealyksScreens.PostUserBodyScreen.route) {
                                            popUpTo(HealyksScreens.ProfileScreen.route) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Fill Details",
                                    copy = 0.75f
                                )
                                CustomButton(
                                    onClick = {
                                        try {
                                            onLogOutClick()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Logout",
                                    copy = 0.75f
                                )
                            }
                        }
                    }
                    is UiState.Failed -> {
                        // Handle 404 error specifically
                        if (getUserState.message.contains("404", ignoreCase = true) == true) {
                            // Show a message if no user details are found
                            LottieAnimation(
                                modifier = Modifier.size(180.dp),
                                composition = noData,
                                iterations = LottieConstants.IterateForever,
                                contentScale = ContentScale.Fit
                            )
                            // Add both Fill Details and Logout buttons
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 38.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CustomButton(
                                    onClick = {
                                        // Navigate to PostUserBodyScreen to fill details
                                        navController.navigate(HealyksScreens.PostUserBodyScreen.route) {
                                            popUpTo(HealyksScreens.ProfileScreen.route) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Fill Details",
                                    copy = 0.75f
                                )
                                CustomButton(
                                    onClick = {
                                        try {
                                            onLogOutClick()
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "Logout Failed", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    label = "Logout",
                                    copy = 0.75f
                                )
                            }
                        } else if (getUserState.message?.contains("network", ignoreCase = true) == true) {
                            // Show "no internet" animation for network errors
                            LottieAnimation(
                                modifier = Modifier.size(180.dp),
                                composition = noInternet,
                                iterations = LottieConstants.IterateForever,
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            // Show a generic error message for other failures
                            Text(
                                text = "Failed to load user details: ${getUserState.message}",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun ProfileDetails(userDetails: UserDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Personal information",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailItem("Age", userDetails.age.toString())
        DetailItem("Blood Group", userDetails.bloodGroup)
        DetailItem("Height", "${userDetails.height} cm")
        DetailItem("Weight", "${userDetails.weight} kg")
        DetailItem("Gender", userDetails.gender)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Allergies",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        userDetails.allergies.forEach { allergy ->
            Text(
                text = "- $allergy",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Chronic Diseases",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        userDetails.chronicDiseases.forEach { disease ->
            Text(
                text = "- $disease",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Medications",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        userDetails.medications.forEach { medication ->
            Text(
                text = "- $medication",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lifestyle",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Smoking: ${if (userDetails.lifestyle.smoking) "Yes" else "No"}",
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Drinking: ${if (userDetails.lifestyle.alcohol) "Yes" else "No"}",
            color = MaterialTheme.colorScheme.onBackground
        )
        userDetails.lifestyle.physicalActivity?.let { frequency ->
            Text(
                text = "Exercise Frequency: $frequency",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}