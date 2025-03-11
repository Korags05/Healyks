package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.healyks.app.R
import com.healyks.app.data.model.DashboardItems
import com.healyks.app.view.components.Carousel.ImageSlider
import com.healyks.app.view.components.core.CustomCard

@Composable
fun DashboardScreen(
    navController: NavController
) {
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
        TopBar(name = "Utkarsh", actions = {
            IconButton(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
                TODO()
            }) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = CircleShape
                ) {
                    Image(
                        painter = painterResource(R.drawable.dark_logo),
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(50.dp)
                    )

                    //TODO
//                    if (profileImageUrl != null) {
//                        Image(
//                            painter = rememberAsyncImagePainter(profileImageUrl),
//                            contentDescription = "Profile Icon",
//                            modifier = Modifier.size(32.dp)
//                        )
//                    } else {
//                        Image(
//                            painter = painterResource(R.drawable.profile),
//                            contentDescription = "Profile Icon",
//                            modifier = Modifier.size(32.dp)
//                        )
//                    }
                }
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
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
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomCard(label = "first aid", onClick = {}, iconRes = R.drawable.firstaid)
                CustomCard(label = "analyze", onClick = {}, iconRes = R.drawable.analyze)
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CustomCard(label = "reminder", onClick = {}, iconRes = R.drawable.reminders)
                CustomCard(label = "feedback", onClick = {}, iconRes = R.drawable.feedback)
            }

        }
    }
}