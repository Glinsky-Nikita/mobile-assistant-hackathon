package com.example.mobileassistant.domain.remote_ai_service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val baseUrl = "http://0.0.0.0:8000"

private val interceptor = HttpLoggingInterceptor()
private val clien = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private fun RemoteAiServiceApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): RemoteAiService {
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .run {if(okHttpClient != null) client(okHttpClient) else this}
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteAiService::class.java)
}

val RemoteAiServiceApi = RemoteAiServiceApi(
    baseUrl = baseUrl,
    okHttpClient = clien
)