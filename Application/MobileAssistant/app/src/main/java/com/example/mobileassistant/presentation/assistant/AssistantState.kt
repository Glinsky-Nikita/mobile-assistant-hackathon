package com.example.mobileassistant.presentation.assistant

import java.io.File

data class AssistantState(
    val lifeCycleStep: LifeCycleStep = LifeCycleStep.Default,
    val isPlaying: Boolean = false,
    val showText: Boolean = false,
    val time: Int = 0,
    val file: File? = null,
    val text: String = "",
    val error: String? = null,
)



sealed class LifeCycleStep {
    data object Default: LifeCycleStep()
    data object Recording: LifeCycleStep()
    data object HaveFile: LifeCycleStep()
    data object Test: LifeCycleStep()
}