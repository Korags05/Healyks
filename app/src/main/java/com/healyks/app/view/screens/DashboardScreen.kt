package com.healyks.app.view.screens

import TopBar
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.R
import com.healyks.app.data.model.DashboardItems
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Error
import com.healyks.app.view.components.Carousel.ImageSlider
import com.healyks.app.view.components.core.CustomCard
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.UserViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val isUserDetailsFilled by userViewModel.isUserDetailsFilled.collectAsState()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.displayName ?: "Mbappe"
    val image = currentUser?.photoUrl?.toString() ?: "drawable://profile"

    val sliderItems = listOf(
        DashboardItems(
            id = "1",
            title = "Title 1",
            description = "Description 1",
            imageUrl = "https://static01.nyt.com/images/2024/04/05/multimedia/05rory-realmadrid-qkcm/05rory-realmadrid-qkcm-mediumSquareAt3X.jpg"
        ),
        DashboardItems(
            id = "2",
            title = "Title 2",
            description = "Description 2",
            imageUrl = "https://a.espncdn.com/combiner/i?img=/media/motion/2025/0309/dm_250309_Mbappe_and_Vinicius_fire_Real_Madrid_past_Rayo_Vallecano/dm_250309_Mbappe_and_Vinicius_fire_Real_Madrid_past_Rayo_Vallecano.jpg&w=1256"
        ),
        DashboardItems(
            id = "3",
            title = "Title 3",
            description = "Description 3",
            imageUrl = "https://a.espncdn.com/combiner/i?img=/media/motion/2025/0309/dm_250309_Mbappe_and_Vinicius_fire_Real_Madrid_past_Rayo_Vallecano/dm_250309_Mbappe_and_Vinicius_fire_Real_Madrid_past_Rayo_Vallecano.jpg&w=1256"
        )
    )
    val scrollState = rememberScrollState()

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
            if (!isUserDetailsFilled) {
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
                    ImageSlider(
                        sliderContents = sliderItems,
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                    )
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
                    CustomCard(label = "analyze", onClick = {}, iconRes = R.drawable.analyze)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CustomCard(label = "reminder", onClick = {}, iconRes = R.drawable.reminders)
                    CustomCard(
                        label = "feedback",
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:")
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("healyks.team@gmail.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "Healyks App Feedback")
                            }
                            context.startActivity(intent)
                        },
                        iconRes = R.drawable.feedback
                    )
                }
            }
        }
    }
}