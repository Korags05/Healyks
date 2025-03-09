package com.healyks.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Coffee,
    onPrimary = Beige,
    primaryContainer = Coffee.copy(alpha = 0.8f),
    onPrimaryContainer = DarkOak,
    secondary = Oak,
    onSecondary = Beige,
    secondaryContainer = Beige.copy(alpha = 0.7f),
    onSecondaryContainer = DarkOak,
    tertiary = Oak.copy(alpha = 0.8f),
    onTertiary = Beige,
    tertiaryContainer = Coffee.copy(alpha = 0.6f),
    onTertiaryContainer = DarkOak,
    background = Beige,
    onBackground = Oak,
    surface = Beige.copy(alpha = 0.9f),
    onSurface = Oak,
    surfaceVariant = Beige.copy(alpha = 0.7f),
    onSurfaceVariant = Oak,
    error = Color(0xFFB3261E),
    onError = Beige,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B)
)

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = Coffee,
    onPrimary = Beige,
    primaryContainer = Coffee.copy(alpha = 0.6f),
    onPrimaryContainer = Beige,
    secondary = Beige,
    onSecondary = DarkOak,
    secondaryContainer = Oak,
    onSecondaryContainer = Beige,
    tertiary = Coffee,
    onTertiary = Beige,
    tertiaryContainer = Oak.copy(alpha = 0.6f),
    onTertiaryContainer = Beige,
    background = DarkOak,
    onBackground = Beige,
    surface = Oak,
    onSurface = Beige,
    surfaceVariant = Oak.copy(alpha = 0.7f),
    onSurfaceVariant = Beige,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC)
)

@Composable
fun HealyksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HealyksTypography,
        content = content
    )
}