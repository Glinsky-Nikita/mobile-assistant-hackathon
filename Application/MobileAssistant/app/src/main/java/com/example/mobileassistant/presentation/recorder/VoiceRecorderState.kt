package com.example.mobileassistant.presentation.recorder

import android.media.MediaRecorder

data class VoiceRecorderState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val error: String? = null,
)