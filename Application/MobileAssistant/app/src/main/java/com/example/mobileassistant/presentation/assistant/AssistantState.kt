package com.example.mobileassistant.presentation.assistant

data class AssistantState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val error: String? = null,
)