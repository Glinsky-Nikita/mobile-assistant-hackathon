package com.example.mobileassistant.presentation.recorder

sealed class RecorderEvent {
    data object StartRecording: RecorderEvent()
    data object PauseRecording: RecorderEvent()
    data object DropRecording: RecorderEvent()
    data object FinishRecording: RecorderEvent()
    data object AttachFile: RecorderEvent()
}