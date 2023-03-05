package com.example.myexpert.repositories

import android.util.Log
import com.example.myexpert.api.GPT3API
import com.example.myexpert.models.Choice
import com.example.myexpert.models.CompletionRequestBody
import com.example.myexpert.models.Message
import com.example.myexpert.utils.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ChatGPTRepository {

    private val tag = "ChatGPTRepository"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Const.GPT3_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()

    private val gpt3API = retrofit.create(GPT3API::class.java)

    /**
     * ApiKeyが有効化を確認
     * @param apiKey
     * @return true(有効) / false(無効)
     */
    fun checkAPIKey(apiKey: String): Boolean {
        val requestBody = getRequestBody(listOf(Message(role = Const.userRole, content = "Hello world!")))
        try {
            val call = gpt3API.getCompletion(requestBody, "Bearer $apiKey")
            val response = call.execute()
            if (response.isSuccessful) {
                Log.i(tag, "Successful: ${response.code()} ${response.message()}")
                return true
            } else {
                Log.e(tag, "Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "Error: $e")
        }
        return false
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @param messages
     * @param apiKey
     * @return Choice / null(取得失敗)
     */
    fun generateText(messages: List<Message>, apiKey: String): Choice? {
        val requestBody = getRequestBody(messages)
        try {
            val call = gpt3API.getCompletion(requestBody, "Bearer $apiKey")
            val response = call.execute()
            if (response.isSuccessful) {
                val choice = response.body()?.choices?.getOrNull(0)
                if (choice != null) {
                    return choice
                }
            } else {
                Log.e(tag, "Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "Error: $e")
        }
        return null
    }

    private fun getRequestBody(messages: List<Message>): CompletionRequestBody {
        return CompletionRequestBody(
            model = "gpt-3.5-turbo",
            messages = messages
        )
    }
}