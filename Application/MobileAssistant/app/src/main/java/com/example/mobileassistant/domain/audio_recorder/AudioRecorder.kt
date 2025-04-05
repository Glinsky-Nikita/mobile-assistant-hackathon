package com.example.mobileassistant.domain.audio_recorder

import android.content.Context
import java.io.File

interface AudioRecorder {

    fun start(outputFile: File)

    fun pause()

    fun stop()
}