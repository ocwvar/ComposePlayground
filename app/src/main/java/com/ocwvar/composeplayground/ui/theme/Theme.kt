package com.ocwvar.composeplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
fun CurrentTheme(isDarkMode: Boolean = isSystemInDarkTheme()): Colors {
    return if (isDarkMode) DarkColorPalette else LightColorPalette
}

@Composable
fun WithTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkMode) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}