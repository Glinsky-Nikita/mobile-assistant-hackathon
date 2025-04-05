package com.example.mobileassistant.domain.use_cases

import android.util.Log
import com.example.mobileassistant.domain.remote_ai_service.AiServiceResponse
import com.example.mobileassistant.domain.remote_ai_service.RemoteAiServiceApi
import com.example.mobileassistant.utils.getMimeTypeFromFileExtension
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class UploadFileUseCase(): UseCase {
    suspend fun execute(file: File): AiServiceResponse {
        val mimeType = getMimeTypeFromFileExtension(file)
        Log.w("UPLOAD_USE_CASE", mimeType.toString())

        //if (mimeType == null) throw

        return try {
            val requestFile = file.asRequestBody("audio/${mimeType}".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("audio_file", file.name, requestFile)
            RemoteAiServiceApi.uploadAudioFile(file = body)
        } catch (e: HttpException) {
            when(e.code()){
                else -> throw e
            }
        }
    }
}