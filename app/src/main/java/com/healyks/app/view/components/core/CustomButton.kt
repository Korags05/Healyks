package com.healyks.app.view.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.healyks.app.ui.theme.Beige
import com.healyks.app.ui.theme.Coffee
import com.healyks.app.ui.theme.Oak

@Composable
fun CustomButton(
    modifier: Modifier,
    onClick: () -> Unit,
    label: String,
    copy: Float,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    weight: FontWeight = FontWeight.Normal
) {
    Button(
        modifier = modifier.fillMaxWidth(copy),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Oak
        ),
        border = BorderStroke(2.dp, Coffee)
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = label,
            color = Beige,
            style = style,
            fontWeight = weight
        )
    }
}