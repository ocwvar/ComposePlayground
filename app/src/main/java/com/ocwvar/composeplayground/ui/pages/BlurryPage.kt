package com.ocwvar.composeplayground.ui.pages

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ocwvar.composeplayground.R
import com.ocwvar.composeplayground.ui.base.Page

@Composable
fun BlurryPage(navController: NavController) {
    Page(pageTitle = "Blurry", actionFunction = { navController.popBackStack() }) {
        BlurryView()
    }
}

@Composable
fun BlurryView() {
    val viewModel: BlurViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize(1.0f)
            .padding(start = 8.dp, end = 8.dp)
    ) {
        // blur control panel
        BlurControlPanel(viewModel)

        Box(
            modifier = Modifier
                .fillMaxSize(1.0f)
                .blur(
                radius = viewModel.boxBlurValue.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .blur(
                        radius = viewModel.textBlurValue.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    ),
                text = stringResource(id = R.string.long_text)
            )

            Box(
                modifier = Modifier
                    .zIndex(1.0f)
                    .width(200.dp)
                    .height(200.dp)
                    .background(Color(0, 0, 0, 180)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    strokeWidth = 5.dp
                )
            }

        }
    }
}

/**
 * Blur value control sliders panel
 */
@Composable
fun BlurControlPanel(viewModel: BlurViewModel) {
    Text(
        modifier = Modifier.padding(top = 12.dp),
        text = "Text blur value: ${viewModel.textBlurValue}",
        style = MaterialTheme.typography.subtitle2
    )
    Slider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        value = viewModel.textBlurValue,
        onValueChange = { viewModel.updateValue(true, it) },
        valueRange = 0.0f..20.0f,
        steps = 20
    )

    Text(
        modifier = Modifier.padding(top = 12.dp),
        text = "All content blur value: ${viewModel.boxBlurValue}",
        style = MaterialTheme.typography.subtitle2
    )
    Slider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        value = viewModel.boxBlurValue,
        onValueChange = { viewModel.updateValue(false, it) },
        valueRange = 0.0f..20.0f,
        steps = 20
    )
}

class BlurViewModel(application: Application) : AndroidViewModel(application) {

    internal var textBlurValue by mutableStateOf(0.0f)
        private set

    internal var boxBlurValue by mutableStateOf(0.0f)
        private set

    /**
     * update blur value
     *
     * @param forText update action for text
     * @param value blur value
     */
    fun updateValue(forText: Boolean, value: Float) {
        if (forText) {
            this.textBlurValue = value
        } else {
            this.boxBlurValue = value
        }
    }

}