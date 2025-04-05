package com.example.mobileassistant.domain.audio_recorder

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.media.audiofx.NoiseSuppressor
import java.text.SimpleDateFormat
import java.util.Date

class VoiceRecorder(
): AudioRecorder {
    private var recorder: MediaRecorder? = null

    @SuppressLint("SimpleDateFormat")
    fun createRecordingFile(): File {
//        val sdf = SimpleDateFormat("dd-MM-yyyy-HH:mm:ss")
//        val currentDateAndTime = sdf.format(Date())

        val directory = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        if (directory == null) {
            throw IOException("Не удалось получить каталог для записи.")
        }
        return File(directory, "recording_${System.currentTimeMillis()}.mp3").apply {
            if (!createNewFile()) {
                throw IOException("Не удалось создать файл для записи.")
            }
        }
    }

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun start(outputFile: File) {
        Log.w("VOICE_RECORDER","record started")

        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(128000)

            setOutputFile(FileOutputStream(outputFile).fd)

            try {
                prepare()
                start()
                recorder = this
            } catch (e: IOException) {
                Log.e("VOICE_RECORDER", "Ошибка при подготовке/запуске записи", e)
                stop()
                throw e
            } catch (e: IllegalStateException) {
                Log.e("VOICE_RECORDER", "Неверное состояние MediaRecorder", e)
                stop()
                throw e
            }
        }
    }

    override fun pause() {
        Log.w("VOICE_RECORDER","record paused")
        TODO("Not yet implemented")
    }

    override fun stop() {
        Log.w("VOICE_RECORDER","record stopped")
        try {
            recorder?.stop()
            recorder?.reset()
        } catch (e: IllegalStateException) {
            Log.e("VOICE_RECORDER", "Ошибка при остановке записи", e)
        } finally {
            recorder = null
        }
    }
}