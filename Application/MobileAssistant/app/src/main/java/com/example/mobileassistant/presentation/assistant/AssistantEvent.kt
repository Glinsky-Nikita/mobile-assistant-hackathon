package com.example.mobileassistant.presentation.assistant

sealed class AssistantEvent {
    data object StartRecording: AssistantEvent()
    data object PauseRecording: AssistantEvent()
    data object StopRecording: AssistantEvent()

    data object StartPlaying: AssistantEvent()
    data object PausePlaying: AssistantEvent()
    data object StopPlaying: AssistantEvent()

    data object SendFile: AssistantEvent()
    data object DeleteFile: AssistantEvent()

    data object AttachFile: AssistantEvent()
}