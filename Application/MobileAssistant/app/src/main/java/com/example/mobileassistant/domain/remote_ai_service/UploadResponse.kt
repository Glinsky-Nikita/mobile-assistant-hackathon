package com.example.mobileassistant.domain.remote_ai_service

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("message") val message: String,
    @SerializedName("filename") val filename: String?,
    @SerializedName("filepath") val filepath: String?,
    @SerializedName("file_id") val fileId: Int,
)

