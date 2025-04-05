package com.example.mobileassistant.utils

import java.io.File

fun getMimeTypeFromFileExtension(file: File): String? {
    val extension = file.extension.lowercase() // Получаем расширение файла в нижнем регистре
    return when (extension) {
        "mp3" -> "audio/mpeg"
        "wav" -> "audio/wav"
        "aac" -> "audio/mp4" // AAC обычно в контейнере MP4
        "ogg" -> "audio/ogg"
        "amr" -> "audio/amr"
        "3gp" -> "audio/3gpp"
        "flac" -> "audio/flac"
        "mid", "midi" -> "audio/midi"
        else -> null // Неизвестный тип
    }
}