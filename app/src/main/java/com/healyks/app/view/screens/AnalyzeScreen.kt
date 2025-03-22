package com.healyks.app.view.screens

import TopBar
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.healyks.app.R
import com.healyks.app.data.model.GeminiBody
import com.healyks.app.state.UiState
import com.healyks.app.ui.theme.Oak
import com.healyks.app.view.components.core.CustomButton
import com.healyks.app.view.components.core.CustomTextField
import com.healyks.app.vm.AnalyzeViewModel
import kotlinx.coroutines.launch

@Composable
fun AnalyzeScreen(
    navController: NavController,
    analyzeViewModel: AnalyzeViewModel = hiltViewModel()
) {
    val loading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val analyze by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.analyze))
    val noInternet by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val symptoms = remember { mutableStateOf("") }
    val postSymptomState = analyzeViewModel.postSymptom.collectAsState().value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Auto-scroll to top when success state is received
    LaunchedEffect(postSymptomState) {
        if (postSymptomState is UiState.Success) {
            scrollState.animateScrollTo(0)
        }
    }

    Scaffold(topBar = {
        TopBar(
            name = "Analyze",
            onBackClick = { navController.navigateUp() },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Main content card - removed weight modifier
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),  // Removed weight(1f)
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, Oak)
            ) {
                when (postSymptomState) {
                    is UiState.Success -> {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Oak),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Conditions".uppercase(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = postSymptomState.data.data?.condition ?: "",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Oak),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Recommendations".uppercase(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = postSymptomState.data.data?.recommendation ?: "",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Oak),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Home Remedies".uppercase(),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = postSymptomState.data.data?.homeRemedies ?: "",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            // Add analyze again button
                            CustomButton(
                                onClick = {
                                    // Reset state
                                    symptoms.value = ""
                                    analyzeViewModel.resetPostSymptomState()
                                },
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(0.8f)
                                    .align(Alignment.CenterHorizontally),
                                label = "Analyze Again",
                                copy = 0.75f,
                            )
                        }
                    }

                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),  // Added fixed padding instead of fillMaxSize
                            contentAlignment = Alignment.Center
                        ) {
                            LottieAnimation(
                                modifier = Modifier.size(200.dp),
                                composition = loading,
                                iterations = LottieConstants.IterateForever
                            )
                        }
                    }

                    is UiState.Failed -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),  // Added fixed padding instead of fillMaxSize
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottieAnimation(
                                modifier = Modifier.size(200.dp),
                                composition = noInternet,
                                iterations = LottieConstants.IterateForever
                            )

                            Text(
                                text = "Connection failed. Please try again.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }

                    else -> { // Initial state
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),  // Changed from fillMaxSize
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "How are you feeling today?",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(vertical = 24.dp)
                            )
                            LottieAnimation(
                                modifier = Modifier.size(300.dp),
                                composition = analyze,
                                iterations = LottieConstants.IterateForever
                            )
                        }
                    }
                }
            }

            // Bottom section (always visible except in success state)
            if (postSymptomState !is UiState.Success) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Oak),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Explain your symptoms",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            CustomTextField(
                                value = symptoms.value,
                                onValueChange = { symptoms.value = it },
                                label = "eg: fever, headache..",  // Placeholder text
                                keyboardOptions = KeyboardOptions.Default,
                                copy = 1f,
                            )
                        }
                    }
                    // Analyze Button
                    CustomButton(
                        onClick = {
                            if (symptoms.value.isNotEmpty()) {
                                coroutineScope.launch {
                                    analyzeViewModel.postSymptom(GeminiBody(symptoms.value))
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter your symptom",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.8f),
                        label = "Analyze",
                        copy = 0.75f,
                    )

                    // Gemini attribution
                    Text(
                        text = "powered by Gemini 2.0 flash",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

