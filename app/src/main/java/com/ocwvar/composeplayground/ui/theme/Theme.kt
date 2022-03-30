package com.ocwvar.composeplayground.ui.theme

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.ocwvar.composeplayground.ui.base.appDayNightMode

private val DarkColorPalette = darkColors(
    primary = Purple200,
    background = PureBlack,
    surface = PureBlack,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    background = PureWhite,
    surface = PureWhite,
)

@Composable
fun isDarkMode(): Boolean {
    if (appDayNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
        return isSystemInDarkTheme()
    }

    return appDayNightMode == AppCompatDelegate.MODE_NIGHT_YES
}

@Composable
fun currentTheme(isDarkMode: Boolean = isDarkMode()): Colors {
    return if (isDarkMode) DarkColorPalette else LightColorPalette
}

@Composable
fun WithTheme(
    isDarkMode: Boolean = isDarkMode(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkMode) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

