package com.ocwvar.composeplayground.ui.pages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavHostController
import com.ocwvar.composeplayground.ui.base.Page
import com.ocwvar.composeplayground.ui.base.PageType
import com.ocwvar.composeplayground.ui.base.model.FunctionModel
import com.ocwvar.composeplayground.ui.base.widget.ListItemView

@ExperimentalUnitApi
@Composable
fun PlaygroundPage(navController: NavHostController) {
    Page(
        pageTitle = "Playground",
        actionIconId = null,
        actionFunction = {}
    ) {
        PlaygroundView(navController = navController)
    }
}

@ExperimentalUnitApi
@Composable
fun PlaygroundView(navController: NavHostController) {
    val pages = listOf(
        FunctionModel(PageType.DynamicList, "Dynamic list testing"),
        FunctionModel(PageType.DialogTesting, "Dialog test"),
        FunctionModel(PageType.MVVM, "MVVM Network request")
    )

    LazyColumn {
        this.items(pages) { page ->
            ListItemView(itemText = page.pageTitle) {
                navController.navigate(page.pageType.name)
            }
        }
    }
}

