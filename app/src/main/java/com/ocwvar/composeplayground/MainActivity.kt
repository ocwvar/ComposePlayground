package com.ocwvar.composeplayground

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ocwvar.composeplayground.ui.base.InitGlobalValues
import com.ocwvar.composeplayground.ui.base.PageType
import com.ocwvar.composeplayground.ui.pages.DialogTestPage
import com.ocwvar.composeplayground.ui.pages.DynamicListPage
import com.ocwvar.composeplayground.ui.pages.MvvmPage
import com.ocwvar.composeplayground.ui.pages.PlaygroundPage
import com.ocwvar.composeplayground.ui.theme.*

@ExperimentalAnimationApi
@ExperimentalUnitApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitGlobalValues()

            ApplyComposeColorToSystemUi()
            Content()
        }
    }

    @Composable
    fun ApplyComposeColorToSystemUi() {
        if (isDarkMode()) {
            window.statusBarColor = DarkStatusBarColor.toArgb()
        } else {
            window.statusBarColor = LightStatusBarColor.toArgb()
        }
        window.setBackgroundDrawable(ColorDrawable(currentTheme().background.toArgb()))
    }
}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun Content() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = PageType.Main.name
    ) {
        this.composable(PageType.Main.name) { PlaygroundPage(navController = navigationController) }
        this.composable(PageType.DynamicList.name) { DynamicListPage(navController = navigationController) }
        this.composable(PageType.DialogTesting.name) { DialogTestPage(navController = navigationController) }
        this.composable(PageType.MVVM.name) { MvvmPage(navController = navigationController) }
    }
}