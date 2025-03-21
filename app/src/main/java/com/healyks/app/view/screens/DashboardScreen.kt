package com.healyks.app.view.screens

import TopBar
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.healyks.app.data.model.DashboardItems
import com.healyks.app.state.UiState
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Error
import com.healyks.app.view.components.Carousel.ImageSlider
import com.healyks.app.view.components.core.CustomCard
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.DashboardViewModel
import com.healyks.app.vm.UserViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val loading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val noInternet by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet))
    val getUserState = userViewModel.getUserState.collectAsState().value
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.displayName ?: "Mbappe"
    val image = currentUser?.photoUrl?.toString() ?: "drawable://profile"
    val dashboardData = dashboardViewModel.dashboardDataByID.collectAsState().value
    val dashboardItems = listOf(dashboardData)
    val scrollState = rememberScrollState()

    // Fetch user details when the screen is launched
    LaunchedEffect(Unit) {
        if (getUserState is UiState.Idle) {
            userViewModel.getUser()
        }
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getAllDashboardData()
    }

    val dashboardListState by dashboardViewModel.allDashboardData.collectAsState()

    Scaffold(topBar = {
        TopBar(
            name = "Hello, $userName",
            actions = {
                IconButton(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
                    navController.navigate(HealyksScreens.ProfileScreen.route) {
                        launchSingleTop = true
                    }
                }) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Coffee)
                    ) {
                        if (image != null) {
                            Image(
                                painter = rememberAsyncImagePainter(image),
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.g),
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
            }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if ((getUserState is UiState.Success && getUserState.data?.data == null) ||
                getUserState is UiState.Failed) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Error
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Please fill up your user details from profile screen",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium,
                            color = Beige
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(46.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .aspectRatio(4f / 3f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {

                    when(val state = dashboardListState) {
                        is UiState.Failed -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LottieAnimation(
                                    modifier = Modifier.size(100.dp),
                                    composition = noInternet,
                                    iterations = LottieConstants.IterateForever,
                                    contentScale = ContentScale.Fit
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
                                    modifier = Modifier.size(100.dp),
                                    composition = loading,
                                    iterations = LottieConstants.IterateForever,
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                        is UiState.Success -> {
                            val dashboardItems = state.data.data ?: emptyList()
                            ImageSlider(
                                sliderContents = dashboardItems,
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CustomCard(
                        label = "first aid",
                        onClick = {
                            navController.navigate(HealyksScreens.FirstAidListScreen.route)
                        },
                        iconRes = R.drawable.firstaid
                    )
                    CustomCard(
                        label = "analyze",
                        onClick = { navController.navigate(HealyksScreens.AnalyzeScreen.route) },
                        iconRes = R.drawable.analyze
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CustomCard(
                        label = "reminder",
                        onClick = { navController.navigate(HealyksScreens.ReminderScreen.route) },
                        iconRes = R.drawable.reminder
                    )
                    CustomCard(
                        label = "Shelyks",
                        onClick = {
                            navController.navigate(HealyksScreens.PeriodTrackerScreen.route)
                        },
                        iconRes = R.drawable.shelyks
                    )
                }
            }
        }
    }
}