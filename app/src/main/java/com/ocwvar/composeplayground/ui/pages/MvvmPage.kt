package com.ocwvar.composeplayground.ui.pages

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ocwvar.composeplayground.ui.base.Page
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

@Composable
fun MvvmPage(navController: NavController) {
    Page(pageTitle = "MVVM Testing", actionFunction = { navController.popBackStack() }) {
        MvvmView()
    }
}

@Composable
fun MvvmView(viewModel: ImageViewModel = viewModel()) {
    val model = viewModel.imageModel

    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, start = 12.dp, end = 12.dp)
        ) {
            if (model.bitmap == null) {
                Text(text = model.message, fontWeight = FontWeight.Bold)
            } else {
                Image(
                    bitmap = model.bitmap.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.width(200.dp).height(150.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            onClick = { viewModel.fetch() }
        ) {
            Text(text = "Fetch image")
        }
    }
}

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ImageRepository = ImageRepository()
    private var lastJob: Job? = null

    var imageModel by mutableStateOf(ImageModel(null, "NO_DATA"))
        private set

    //unhandled exception handler for viewModelScope
    private val unhandledExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            //any unhandled exception that occurred during [fetchJob] will end up here
            println("WE HAVE AN UNHANDLED EXCEPTION: $throwable")
            imageModel = ImageModel(null, throwable.message ?: "ERROR")
        }

    fun fetch() {
        this.imageModel = ImageModel(null, "LOADING...")
        this.lastJob?.cancel()
        this.lastJob = viewModelScope.launch(this.unhandledExceptionHandler) {
            val result: Bitmap? = withContext(Dispatchers.IO) {
                val bytes: ByteArray = repository.fetch()
                if (bytes.isNotEmpty()) {
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                } else {
                    null
                }
            }

            imageModel = ImageModel(result, "")
        }
    }


    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun saveAsImage(context: Context, bytes: ByteArray): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "CN")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        with(context.contentResolver) {
            val dbUri: Uri? = this.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            dbUri ?: return@with

            kotlin.runCatching {
                openOutputStream(dbUri)?.use {
                    it.write(bytes)
                    return dbUri
                }
            }
        }

        return null
    }
}

class ImageRepository {
    private val source: String = "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png250px/cn.png"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .build()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun fetch(): ByteArray {
        val request: Request = Request.Builder()
            .url(this.source)
            .build()

        val response: Response = this.client.newCall(request).execute()
        if (!response.isSuccessful) return ByteArray(0)

        return response.body?.bytes() ?: kotlin.run { ByteArray(0) }
    }

}

data class ImageModel(val bitmap: Bitmap?, val message: String)