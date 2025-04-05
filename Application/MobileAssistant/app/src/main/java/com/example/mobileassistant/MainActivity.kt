package com.example.mobileassistant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.mobileassistant.domain.audio_player.VoicePlayer
import com.example.mobileassistant.domain.audio_recorder.VoiceRecorder
import com.example.mobileassistant.presentation.assistant.VoiceRecorderScreen
import com.example.mobileassistant.presentation.assistant.AssistantViewModel
import com.example.mobileassistant.ui.theme.MobileAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobileAssistantTheme {
                RequestAudioPermissionScreen {}

                val recorder = VoiceRecorder(applicationContext)
                val player = VoicePlayer(applicationContext)
                val viewModel = AssistantViewModel(
                    recorder = recorder,
                    player = player,
                )

                VoiceRecorderScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RequestAudioPermissionScreen(onPermissionResult: (Boolean) -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            hasPermission = isGranted
            onPermissionResult(isGranted) // Сообщаем результат в MainActivity
        }
    )

    LaunchedEffect(key1 = true) {
        if (!hasPermission) {
            Log.i("PermissionRequest", "Запрашиваем разрешение на запись аудио")
            launcher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            Log.i("PermissionRequest", "Разрешение на запись аудио уже есть")
            onPermissionResult(true) // Сообщаем, что разрешение уже есть
        }
    }
}

@Composable
fun mainScreen(
    viewModel: AssistantViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileAssistantTheme {
        Greeting("Android")
    }
}