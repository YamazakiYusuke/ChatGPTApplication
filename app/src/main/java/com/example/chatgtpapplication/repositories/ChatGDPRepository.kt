package com.example.chatgtpapplication.repositories

import android.util.Log
import com.example.chatgtpapplication.api.GPT3API
import com.example.chatgtpapplication.model.CompletionRequestBody
import com.example.chatgtpapplication.utils.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatGDPRepository {

    private val tag = "ChatGDPRepository"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Const.GPT3_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val gpt3API = retrofit.create(GPT3API::class.java)
    private var context: String? = null

    /**
     * ApiKeyが有効化を確認
     * @param apiKey
     * @return true(有効) / false(無効)
     */
    fun checkAPIKey(apiKey: String): Boolean {
        val requestBody = getRequestBody("こんにちは")
        try {
            val call = gpt3API.getCompletion(requestBody, "Bearer $apiKey", context)
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

    fun generateText(prompt: String, apiKey: String): String? {
        val requestBody = getRequestBody(prompt)
        val call = gpt3API.getCompletion(requestBody, "Bearer $apiKey", context)
        val response = call.execute()
        if (response.isSuccessful) {
            val completions = response.body()?.completions
            if (completions != null && completions.isNotEmpty()) {
                this.context = response.body()?.context
                return completions[0].text
            }
        } else {
            Log.e(tag, "Error: ${response.code()} ${response.message()}")
        }
        return null
    }

    private fun getRequestBody(prompt: String): CompletionRequestBody {
        return CompletionRequestBody(
            prompt = prompt,
            temperature = 0.7f,
            maxTokens = 50,
            stop = null,
            model = "davinci"
        )
    }
}