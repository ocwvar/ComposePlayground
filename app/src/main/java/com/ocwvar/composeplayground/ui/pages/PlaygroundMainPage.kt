package com.ocwvar.composeplayground.ui.pages

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ocwvar.composeplayground.ui.base.Page
import com.ocwvar.composeplayground.ui.base.PageType
import com.ocwvar.composeplayground.ui.base.appDayNightMode
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

    val viewModel: DayNightViewModel = viewModel()

    val pages = listOf(
        FunctionModel(PageType.DynamicList, "Dynamic list testing"),
        FunctionModel(PageType.DialogTesting, "Dialog test"),
        FunctionModel(PageType.MVVM, "MVVM Network request")
    )

    Column(
        modifier = Modifier
            .fillMaxSize(1.0f)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        //panel that control day-night
        DayNightControlRow(viewModel)

        // content list
        LazyColumn {
            this.items(pages) { page ->
                ListItemView(itemText = page.pageTitle) {
                    navController.navigate(page.pageType.name)
                }
            }
        }
    }
}

/**
 * A panel that contain [DayNightButton]
 */
@Composable
fun DayNightControlRow(
    viewModel: DayNightViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(1.0f),
        horizontalArrangement = Arrangement.Center
    ) {
        DayNightButton(
            viewModel = viewModel,
            targetMode = AppCompatDelegate.MODE_NIGHT_NO,
            modifier = Modifier
                .weight(weight = 1.0f)
                .padding(end = 8.dp)
        )

        DayNightButton(
            viewModel = viewModel,
            targetMode = AppCompatDelegate.MODE_NIGHT_YES,
            modifier = Modifier
                .weight(weight = 1.0f)
                .padding(end = 8.dp)
        )

        DayNightButton(
            viewModel = viewModel,
            targetMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            modifier = Modifier.weight(weight = 1.0f)
        )
    }
}

/**
 * Button that can control DayNight mode
 */
@Composable
fun DayNightButton(
    viewModel: DayNightViewModel,
    targetMode: Int,
    modifier: Modifier
) {
    Button(
        modifier = modifier,
        onClick = { viewModel.switch(targetMode) },
        enabled = viewModel.currentState != targetMode
    ) {
        Text(
            text = when(targetMode){
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> "AUTO"
                AppCompatDelegate.MODE_NIGHT_YES -> "Night"
                AppCompatDelegate.MODE_NIGHT_NO -> "Light"
                else -> throw IllegalArgumentException("Invalid mode:$targetMode for day-night mode")
            }
        )
    }
}

class DayNightViewModel(application: Application) : AndroidViewModel(application) {

    internal var currentState by mutableStateOf(appDayNightMode)
        private set

    /**
     * switch day-night mode
     * mode should be one of:
     * [AppCompatDelegate.MODE_NIGHT_YES]
     *
     * [AppCompatDelegate.MODE_NIGHT_NO]
     *
     * [AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM]
     *
     * @see AppCompatDelegate.setDefaultNightMode
     * @param mode Mode value
     */
    fun switch(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
        appDayNightMode = mode
        this.currentState = appDayNightMode
    }

}

