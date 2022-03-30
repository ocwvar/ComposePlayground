package com.ocwvar.composeplayground.ui.base

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var appDayNightMode by mutableStateOf(AppCompatDelegate.getDefaultNightMode())

@Composable
fun InitGlobalValues() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    appDayNightMode = AppCompatDelegate.getDefaultNightMode()
}

