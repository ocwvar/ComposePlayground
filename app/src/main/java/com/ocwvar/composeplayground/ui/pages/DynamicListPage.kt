package com.ocwvar.composeplayground.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import com.ocwvar.composeplayground.ui.base.Page
import com.ocwvar.composeplayground.ui.base.widget.ListItemView
import kotlinx.coroutines.launch
import kotlin.random.Random

@ExperimentalUnitApi
@Composable
fun DynamicListPage(navController: NavController) {
    Page(
        pageTitle = "Dynamic list",
        actionIconId = Icons.Default.Close,
        actionFunction = {
            navController.popBackStack()
        }
    ) {
        DynamicListView()
    }
}

@ExperimentalUnitApi
@SuppressLint("UnrememberedMutableState")
@Composable
fun DynamicListView() {
    val list = mutableStateListOf<String>()
    val listState = rememberLazyListState()
    val composableCoroutine = rememberCoroutineScope()

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(1.0f),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                list.add(getRandomNumberString())
                composableCoroutine.launch {
                    listState.animateScrollToItem(list.size)
                }
            }) {
                Text(text = "ADD")
            }
            
            Spacer(modifier = Modifier.width(Dp(10f)))

            Button(onClick = {
                if (list.isNotEmpty()) list.removeLast()
            }) {
                Text(text = "REMOVE")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(Dp(5.0f)),
            state = listState
        ) {
            items(list) { item ->
                ListItemView(itemText = item) {

                }
            }
        }
    }
}

fun getRandomNumberString(): String {
    return Random(System.currentTimeMillis()).nextLong().toString()
}