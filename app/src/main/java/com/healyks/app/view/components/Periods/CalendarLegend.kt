package com.healyks.app.view.components.Periods

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Oak

@Composable
fun CalendarLegend() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer.copy(0.7f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSurface,
            text = "Legend",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Red.copy(alpha = 0.7f), CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Period Start/End",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Magenta.copy(alpha = 0.7f), CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Next Period",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Blue.copy(alpha = 0.7f), CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Ovulation",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Green.copy(alpha = 0.5f), CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Fertile Window",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}