package com.ocwvar.composeplayground.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ocwvar.composeplayground.ui.base.Page

@Composable
fun DialogTestPage(navController: NavController) {
    Page(pageTitle = "Dialog testing", actionFunction = { navController.popBackStack() }) {
        DialogTestView()
    }
}

@Composable
fun DialogTestView(viewModel: DialogTestPageViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(1.0f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewModel.shouldShowDialog) {
            CustomDialog(viewModel)
        }

        OutlinedTextField(
            value = viewModel.content,
            onValueChange = { viewModel.updateContent(it) },
            placeholder = { Text(text = "type here") },
            label = { Text(text = "Dialog content") },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp)
        )

        Spacer(modifier = Modifier.height(Dp(6f)))

        Button(onClick = { viewModel.showDialog() }) {
            Text(text = "Fire a dialog")
        }
    }
}

@Composable
fun CustomDialog(viewModel: DialogTestPageViewModel) {
    Dialog(onDismissRequest = { viewModel.hideDialog() }) {
        Card(
            shape = RoundedCornerShape(20.0f),
            elevation = 10.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(start = 20.dp, end = 20.dp, bottom = 12.dp, top = 20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryVariant)
                )

                Text(
                    modifier = Modifier.padding(bottom = 18.dp),
                    text = viewModel.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
                    text = viewModel.content,
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier
                        .background(
                            color = Color(
                                red = MaterialTheme.colors.primary.red,
                                blue = MaterialTheme.colors.primary.blue,
                                green = MaterialTheme.colors.primary.green,
                                alpha = 0.1f,
                            )
                        )
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { viewModel.hideDialog() },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = "Done", fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = { viewModel.hideDialog() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Cancel", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

class DialogTestPageViewModel : ViewModel() {
    var shouldShowDialog by mutableStateOf(false)
        private set

    var content by mutableStateOf("However, \"buttons\" slot was not wrapped, and my solution was like in following code (\"title\" and \"text\" slot must be set to null and all of dialog content goes into \"buttons\" slot)")
        private set

    val title: String = "Compose Dialog Title"

    fun hideDialog() {
        shouldShowDialog = false
    }

    fun showDialog() {
        shouldShowDialog = true
    }

    fun updateContent(content: String) {
        this.content = content
    }

}