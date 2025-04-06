package com.example.mobileassistant.domain.remote_ai_service

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("message") val message: String,
    @SerializedName("filename") val filename: String?,
    @SerializedName("filepath") val filepath: String?,
    @SerializedName("file_diar") val fileDiar: String?,
    @SerializedName("file_summ") val fileSumm: String?,
    @SerializedName("file_id") val fileId: Int,
)
