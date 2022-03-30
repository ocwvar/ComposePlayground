package com.ocwvar.composeplayground.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ocwvar.composeplayground.ui.theme.WithTheme

@Composable
fun Page(
    pageTitle: String,
    actionIconId: ImageVector? = Icons.Default.Close,
    actionFunction: () -> Unit,
    content: @Composable () -> Unit,
) {
    WithTheme(content = {
        Surface(modifier = Modifier.fillMaxSize(1.0f)) {
            Column {
                TopAppBarByCase(
                    title = pageTitle,
                    actionIconId = actionIconId,
                    actionFunction = actionFunction
                )
                content()
            }
        }
    })
}

@Composable
fun TopAppBarByCase(title: String, actionIconId: ImageVector?, actionFunction: () -> Unit) {
    if (actionIconId != null) {
        println("### ${MaterialTheme.colors.primarySurface}")
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = actionFunction) {
                    Icon(imageVector = actionIconId, contentDescription = "")
                }
            }
        )
    } else {
        TopAppBar(title = { Text(text = title) })
    }
}

enum class PageType {
    Main,
    DynamicList,
    DialogTesting,
    MVVM,
    Blurry
}