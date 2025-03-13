package com.healyks.app.view.screens

import TopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.healyks.app.view.navigation.HealyksScreens
import com.healyks.app.vm.FirstAidViewModel

@Composable
fun FirstAidDetailScreen(
    navController: NavController,
    firstAidViewModel: FirstAidViewModel = hiltViewModel(
        navController.getBackStackEntry(HealyksScreens.FirstAidListScreen.route)
    )
) {
    val item = firstAidViewModel.selectedItem
    item?.let {
        Scaffold(
            topBar = {
                TopBar(
                    name = it.name,
                    onBackClick = { navController.navigateUp() }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                DetailCard(title = "Treatment", content = it.treatment)
                DetailCard(title = "When to Seek Help", content = it.whenToSeekHelp)
            }
        }
    } ?: run {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "No item selected",
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun DetailCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, style = MaterialTheme.typography.bodyLarge)
        }
    }
}