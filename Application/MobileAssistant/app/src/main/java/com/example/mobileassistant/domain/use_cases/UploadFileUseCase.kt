package com.example.mobileassistant.domain.use_cases

import android.util.Log
import com.example.mobileassistant.domain.remote_ai_service.FileResponse
import com.example.mobileassistant.domain.remote_ai_service.RetrofitClient
import com.example.mobileassistant.domain.remote_ai_service.UploadResponse

import com.example.mobileassistant.utils.getMimeTypeFromFileExtension
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File


class NotFoundException(
    override val message: String? = "Not found exception"
) : Exception(message)

class UploadFileUseCase() : UseCase {
    suspend fun execute(file: File): FileResponse {
        val mimeType = getMimeTypeFromFileExtension(file)

        //if (mimeType == null) throw

        return try {


            val requestFile = file.asRequestBody("audio/${mimeType}".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = RetrofitClient.apiService.uploadFile(file = body)
            //Log.w("UPLOAD_USE_CASEafsdfgsdfgs", "response: ${response.fileId}")
            response

        } catch (e: HttpException) {
            Log.w("UPLOAD_USE_CASE httpexception", e.code().toString())
            when (e.code()) {
                404 -> throw NotFoundException()
                else -> throw e
            }
        } catch (e: Exception) {
            Log.w("UPLOAD_USE_CASE exception", e.message.toString())
            throw e
        }
    }
}

