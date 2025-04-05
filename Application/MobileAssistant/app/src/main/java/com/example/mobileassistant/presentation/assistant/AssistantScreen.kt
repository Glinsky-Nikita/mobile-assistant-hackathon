package com.example.mobileassistant.presentation.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileassistant.ui.theme.GrayBackground

@Composable
fun VoiceRecorderScreen(
    viewModel: AssistantViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackground)
            
    ) {
        Button(
            onClick = { viewModel.onEvent(AssistantEvent.StartRecording) }
        ) {
            Text(text = "start record")
        }

        Button(
            onClick = { viewModel.onEvent(AssistantEvent.StopRecording) }
        ) {
            Text(text = "stop record")
        }

        Button(
            onClick = { viewModel.onEvent(AssistantEvent.StartPlaying) }
        ) {
            Text(text = "start play")
        }

        Button(
            onClick = { viewModel.onEvent(AssistantEvent.StopPlaying) }
        ) {
            Text(text = "stop play")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun VoiceRecorderScreenPreview(){
    val vm = AssistantViewModel(null, null)
    VoiceRecorderScreen(vm)
}