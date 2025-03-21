package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.healyks.app.R
import com.healyks.app.state.UiState
import com.healyks.app.vm.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    navController: NavController,
    dashboardId: String,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val loading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val noInternet by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val scrollState = rememberScrollState()

    // Fetch the dashboard data when the screen is launched
    LaunchedEffect(dashboardId) {
        dashboardViewModel.getDashboardDataById(dashboardId)

    }

    // Observe the dashboard data state
    val dashboardState by dashboardViewModel.dashboardDataByID.collectAsState()


    Scaffold(topBar = {
        TopBar(
            name = "Details",
            onBackClick = { navController.navigateUp() },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = dashboardState) {
                is UiState.Failed -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            modifier = Modifier.height(200.dp),
                            composition = noInternet,
                            iterations = LottieConstants.IterateForever,
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                UiState.Idle -> {
                    dashboardViewModel.getAllDashboardData()
                }
                UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieAnimation(
                            modifier = Modifier.height(200.dp),
                            composition = loading,
                            iterations = LottieConstants.IterateForever,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                is UiState.Success -> {
                    val dashboardData = state.data.data
                    if (dashboardData != null) {
                        // Title section
                        Text(
                            text = dashboardData.title.uppercase(),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        // Content section
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = dashboardData.content,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        AsyncImage(
                            model = dashboardData.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)), // Optional rounded corners
                            contentScale = ContentScale.Inside // Ensures proper display
                        )

                    } else {
                        // No data available
                        Text(
                            text = "No details available for this dashboard item",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}