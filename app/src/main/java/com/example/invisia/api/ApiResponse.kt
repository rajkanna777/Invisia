package com.example.invisia.api

data class ApiResponse<T>(
    val data: T? = null,
    val errorMessage: String? = null
)