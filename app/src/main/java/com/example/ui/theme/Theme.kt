package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MuseumColorScheme = darkColorScheme(
    primary = MuseumGoldPrimary,
    onPrimary = MuseumObsidian,
    primaryContainer = MuseumGoldContainer,
    onPrimaryContainer = MuseumGoldOnContainer,
    secondary = MuseumGoldLight,
    onSecondary = MuseumObsidian,
    tertiary = MuseumHighlightAzure,
    background = DarkBackground,
    onBackground = MuseumMarbleWhite,
    surface = DarkSurface,
    onSurface = MuseumMarbleWhite,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = MuseumSubtleGray,
    outline = DarkOutline,
    outlineVariant = MuseumObsidianBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = DarkBackground.toArgb()
                window.navigationBarColor = DarkBackground.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = MuseumColorScheme,
        typography = MuseumTypography,
        content = content
    )
}
