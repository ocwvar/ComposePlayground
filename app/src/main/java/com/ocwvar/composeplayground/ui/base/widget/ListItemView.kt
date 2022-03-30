package com.ocwvar.composeplayground.ui.base.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@ExperimentalUnitApi
@Composable
fun ListItemView(itemText: String, enabled: Boolean = true, onItemClick: () -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth(fraction = 1.0f)
            .clickable(enabled = enabled, onClick = onItemClick)
            .padding(Dp(12f)),
        text = itemText,
        fontSize = TextUnit(16f, TextUnitType.Sp),
        style = TextStyle(color = if (enabled) Color.Black else Color.LightGray)
    )
}

@ExperimentalUnitApi
@Composable
fun ListItemView(itemText: String) {
    Text(
        modifier = Modifier
            .padding(Dp(12f))
            .fillMaxWidth(fraction = 1.0f),
        text = itemText,
        fontSize = TextUnit(16f, TextUnitType.Sp),
        color = Color.LightGray
    )
}