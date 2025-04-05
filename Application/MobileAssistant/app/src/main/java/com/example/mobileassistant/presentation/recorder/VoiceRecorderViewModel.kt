package com.example.mobileassistant.presentation.recorder

import android.media.MediaRecorder
import androidx.lifecycle.ViewModel
import com.example.mobileassistant.domain.audio_recorder.AudionRecorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VoiceRecorderViewModel(
    recorder: AudionRecorder
): ViewModel() {
    private val _uiState = MutableStateFlow(VoiceRecorderState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RecorderEvent) = when(event) {
        is RecorderEvent.StartRecording -> onStartRecordingEvent()
        is RecorderEvent.PauseRecording -> Unit
        is RecorderEvent.FinishRecording -> Unit
        is RecorderEvent.AttachFile -> Unit
        is RecorderEvent.DropRecording -> Unit
    }

    private fun onStartRecordingEvent() {

    }
}