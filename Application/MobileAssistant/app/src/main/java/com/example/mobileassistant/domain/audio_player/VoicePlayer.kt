package com.example.mobileassistant.domain.audio_player

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class VoicePlayer(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playAudio(file: File) {
        Log.w("VOICE_PLAYER","play started")
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        Log.w("VOICE_PLAYER","play stoped")
        player?.stop()
        player?.release()
        player = null
    }
}