package com.example.myexpert.api

import com.example.myexpert.models.CompletionRequestBody
import com.example.myexpert.models.CompletionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GPT3API {
    @Headers("Content-Type: application/json")
    @POST("completions")
    fun getCompletion(
        @Body requestBody: CompletionRequestBody,
        @Header("Authorization") apiKey: String
    ): Call<CompletionResponse>
}

