package `in`.iotkiit.raidersreckoningapp.view.components.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healyks.app.R

@Composable
fun GoogleOneTapButton(
    iconOnly: Boolean = false,
    theme: GoogleButtonTheme = if (isSystemInDarkTheme()) GoogleButtonTheme.Dark
    else GoogleButtonTheme.Light,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = when (theme) {
            GoogleButtonTheme.Light -> MaterialTheme.colorScheme.primary
            GoogleButtonTheme.Dark -> MaterialTheme.colorScheme.primary
            GoogleButtonTheme.Neutral -> Color(0xFFF2F2F2)
        },
        contentColor = when (theme) {
            GoogleButtonTheme.Dark -> Color(0xFFE3E3E3)
            else -> Color(0xFF1F1F1F)
        },
    ),
    shape: Shape = ButtonDefaults.shape,
    onClick: (() -> Unit)? = null,
) {
    Button(
        modifier = Modifier.width(if (iconOnly) 40.dp else Dp.Unspecified),
        onClick = {
            onClick?.invoke()
        },
        shape = shape,
        colors = colors,
        contentPadding = PaddingValues(horizontal = if (iconOnly) 9.5.dp else 12.dp)
    ) {
        Row (
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.g),
                contentDescription = "Google Logo"
            )
            Spacer(Modifier.width(10.dp))
            if (!iconOnly) {
                Text(
                    text = "Sign in with Google",
                    maxLines = 1,
                    fontFamily = Exo2,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }

    }
}

enum class GoogleButtonTheme { Light, Dark, Neutral }

private val Exo2 = FontFamily(
    Font(
        resId = R.font.exo2_regular, weight = FontWeight.Medium, style = FontStyle.Normal
    )
)

@Preview
@Composable
private fun LightIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Light,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun DarkIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Dark,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun NeutralIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Neutral,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun LightFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Light,
    )
}

@Preview
@Composable
private fun DarkFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Dark,
    )
}

@Preview
@Composable
private fun NeutralFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Neutral,
    )
}
