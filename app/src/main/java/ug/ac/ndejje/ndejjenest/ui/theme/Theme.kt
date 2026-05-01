package ug.ac.ndejje.ndejjenest.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF64B5F6), // Light blue accent for dark mode
    onPrimary = Color(0xFF050B18), // Dark background color for text on primary
    secondary = PrimaryYellow,    // Branding yellow moved to secondary
    onSecondary = Color.Black,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDarkBlue,
    onPrimary = Color.White,
    secondary = PrimaryYellow,
    background = Color(0xFFF8F9FA), // Off-white for clean look
    surface = Color.White,
    onBackground = PrimaryDarkBlue,
    onSurface = PrimaryDarkBlue
)

@Composable
fun NdejjeNestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor to false to maintain strict branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}