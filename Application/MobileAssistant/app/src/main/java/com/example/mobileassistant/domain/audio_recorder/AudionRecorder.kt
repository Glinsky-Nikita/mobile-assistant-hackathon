package com.example.mobileassistant.domain.audio_recorder

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class AudionRecorder(
    private val contex: Context
): AudioRecorder {
    private var recorder: MediaRecorder? = null

    override fun start(outputFile: File) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}