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
    primary = AccentBlue,
    onPrimary = Color.White,
    secondary = SkyBlue,
    onSecondary = Color.Black,
    tertiary = NavyBlue,
    onTertiary = Color.White,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = Color(0xFF1E2D42),
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = TextGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDarkBlue,
    onPrimary = Color.White,
    secondary = AccentBlue,
    onSecondary = Color.White,
    tertiary = SkyBlue,
    onTertiary = Color.Black,
    background = Color(0xFFF8F9FA),
    surface = Color.White,
    surfaceVariant = Color(0xFFEEEEEE),
    onBackground = PrimaryDarkBlue,
    onSurface = PrimaryDarkBlue,
    onSurfaceVariant = Color.Gray
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