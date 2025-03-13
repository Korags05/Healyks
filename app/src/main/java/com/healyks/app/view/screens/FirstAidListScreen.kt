package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.view.components.FirstAid.ExpandableCard
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.FirstAidViewModel

@Composable
fun FirstAidListScreen(
    navController: NavController,
    firstAidViewModel: FirstAidViewModel = hiltViewModel()
) {

    val firstAidCategories = firstAidViewModel.firstAidItems.groupBy { it.category }

    Scaffold(
        topBar = {
            TopBar(
                name = "First Aid",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            firstAidCategories.forEach { (category, items) ->
                ExpandableCard(
                    category = category,
                    list = items,
                    firstAidViewModel = firstAidViewModel,
                    navigateToDetail = {
                        navController.navigate(HealyksScreens.FirstAidDetailScreen.route)
                    }
                )
            }
        }
    }
}
