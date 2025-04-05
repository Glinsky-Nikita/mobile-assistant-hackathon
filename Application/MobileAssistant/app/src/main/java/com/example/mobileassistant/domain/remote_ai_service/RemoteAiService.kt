package com.example.mobileassistant.domain.remote_ai_service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RemoteAiService {
    @Multipart
    @POST("")
    suspend fun uploadAudioFile(
        @Part file: MultipartBody.Part
    ): AiServiceResponse
}