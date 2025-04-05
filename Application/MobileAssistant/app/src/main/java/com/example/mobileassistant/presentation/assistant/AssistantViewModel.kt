package com.example.mobileassistant.presentation.assistant

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileassistant.domain.audio_player.AudioPlayer
import com.example.mobileassistant.domain.audio_recorder.AudioRecorder
import com.example.mobileassistant.domain.audio_recorder.VoiceRecorder
import com.example.mobileassistant.domain.use_cases.UploadFileUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class AssistantViewModel(
    val recorder: AudioRecorder?,
    val player: AudioPlayer?,
): ViewModel() {
    private val _uiState = MutableStateFlow(AssistantState())
    val uiState = _uiState.asStateFlow()

    private val uploadFileUseCase = UploadFileUseCase()

    fun onEvent(event: AssistantEvent) = when(event) {
        is AssistantEvent.StartRecording -> onStartRecording()
        is AssistantEvent.PauseRecording -> Unit
        is AssistantEvent.StopRecording -> onStopRecording()

        is AssistantEvent.StartPlaying -> onStartPlaying()
        is AssistantEvent.PausePlaying -> Unit
        is AssistantEvent.StopPlaying -> onStopPlaying()

        is AssistantEvent.SendFile -> onSendFile()
        is AssistantEvent.DeleteFile -> Unit

        is AssistantEvent.AttachFile -> Unit
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = throwable.message.toString()
        _uiState.value = _uiState.value.copy(
            error = msg
        )
        Log.e(TAG, "ERROR MESSAGE: ${_uiState.value.error}" )
    }

    private fun onSendFile() {

        val file = _uiState.value.file

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

            if (file != null){

                val transcription = uploadFileUseCase.execute(file)

                _uiState.value = _uiState.value.copy(
                    transcription = transcription.message
                )

            } else {

                _uiState.value = _uiState.value.copy(
                    error = "File is null!"
                )

                Log.w("VIEW_MODEL", _uiState.value.error!!)
            }

        }
    }


    private fun onStartRecording() {
        val file = (recorder as VoiceRecorder).createRecordingFile()
        _uiState.value = _uiState.value.copy(
            file = file
        )
        recorder.start(_uiState.value.file!!)

        _uiState.value = _uiState.value.copy(
            lifeCycleStep = LifeCycleStep.Recording
        )
    }

    private fun onStopRecording() {
        recorder?.stop()

        _uiState.value = _uiState.value.copy(
            lifeCycleStep = LifeCycleStep.HaveFile
        )
    }

    private fun onStartPlaying() {
        player?.playAudio(_uiState.value.file!!)

    }

    private fun onStopPlaying() {
        player?.stop()

    }
}