package com.example.chatgptapplication.repositories

import android.util.Log
import com.example.chatgptapplication.api.GPT3API
import com.example.chatgptapplication.model.CompletionRequestBody
import com.example.chatgptapplication.utils.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ChatGDPRepository {

    private val tag = "ChatGDPRepository"
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

    /**
     * ChatGPTにテキスト生成をリクエスト
     */
    fun generateText(prompt: String, apiKey: String): String? {
        val requestBody = getRequestBody(prompt)
        try {
            val call = gpt3API.getCompletion(requestBody, "Bearer $apiKey", context)
            val response = call.execute()
            if (response.isSuccessful) {
                this.context = response.body()?.context
                val choices = response.body()?.choices
                if (choices != null && choices.isNotEmpty()) {
                    return choices[0].text
                }
            } else {
                Log.e(tag, "Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "Error: $e")
        }
        return null
    }

    private fun getRequestBody(prompt: String): CompletionRequestBody {
        return CompletionRequestBody(
            prompt = prompt,
            temperature = 0.7f,
            maxTokens = 1024,
            model = "text-davinci-003"
        )
    }
}