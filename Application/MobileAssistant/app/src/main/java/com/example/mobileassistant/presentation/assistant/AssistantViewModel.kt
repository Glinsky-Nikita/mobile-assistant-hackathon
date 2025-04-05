package com.example.mobileassistant.presentation.assistant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobileassistant.domain.audio_player.AudioPlayer
import com.example.mobileassistant.domain.audio_recorder.AudioRecorder
import com.example.mobileassistant.domain.audio_recorder.VoiceRecorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class AssistantViewModel(
    val recorder: AudioRecorder?,
    val player: AudioPlayer?,
): ViewModel() {
    private val _uiState = MutableStateFlow(AssistantState())
    val uiState = _uiState.asStateFlow()
    private var audioFile = MutableLiveData<File?>()

    fun onEvent(event: AssistantEvent) = when(event) {
        is AssistantEvent.StartRecording -> onStartRecording()
        is AssistantEvent.PauseRecording -> Unit
        is AssistantEvent.StopRecording -> onStopRecording()

        is AssistantEvent.StartPlaying -> onStartPlaying()
        is AssistantEvent.PausePlaying -> Unit
        is AssistantEvent.StopPlaying -> onStopPlaying()

        is AssistantEvent.AttachFile -> Unit
    }


    private fun onStartRecording() {
        audioFile.value = (recorder as VoiceRecorder).createRecordingFile()
        recorder.start(audioFile.value!!)
    }

    private fun onStopRecording() {
        recorder?.stop()
    }

    private fun onStartPlaying() {
        player?.playAudio(audioFile.value!!)
    }

    private fun onStopPlaying() {
        player?.stop()
    }
}