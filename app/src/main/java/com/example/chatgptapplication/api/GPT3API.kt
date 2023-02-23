package com.example.chatgptapplication.api

import com.example.chatgptapplication.model.CompletionRequestBody
import com.example.chatgptapplication.model.CompletionResponse
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
        @Header("Authorization") apiKey: String,
        @Header("OpenAI-Context") context: String?
    ): Call<CompletionResponse>
}

