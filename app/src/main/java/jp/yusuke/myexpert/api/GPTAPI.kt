package jp.yusuke.myexpert.api

import jp.yusuke.myexpert.models.CompletionRequestBody
import jp.yusuke.myexpert.models.CompletionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GPTAPI {
    @Headers("Content-Type: application/json")
    @POST("completions")
    fun getCompletion(
        @Body requestBody: CompletionRequestBody,
        @Header("Authorization") apiKey: String
    ): Call<CompletionResponse>
}

