package com.example.mobileassistant.domain.remote_ai_service

data class AiServiceResponse(
    val message: String,
    val filename: String?,
    val filepath: String?,
)
