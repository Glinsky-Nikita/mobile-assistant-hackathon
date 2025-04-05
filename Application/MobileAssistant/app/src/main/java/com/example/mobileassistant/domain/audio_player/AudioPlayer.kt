package com.example.mobileassistant.domain.audio_player

import java.io.File

interface AudioPlayer {
    fun playAudio(file:File)
    fun stop()
}