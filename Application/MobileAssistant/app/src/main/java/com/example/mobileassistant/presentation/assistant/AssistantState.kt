package com.example.mobileassistant.presentation.assistant

import java.io.File

data class AssistantState(
    val lifeCycleStep: LifeCycleStep = LifeCycleStep.Default,
    val file: File? = null,
    val transcription: String = "",
    val error: String? = null,
)

sealed class LifeCycleStep {
    data object Default: LifeCycleStep()
    data object Recording: LifeCycleStep()
    data object Recorded: LifeCycleStep()
    data object Playing: LifeCycleStep()
    data object HaveFile: LifeCycleStep()
}