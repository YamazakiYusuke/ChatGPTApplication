package com.example.chatgptapplication.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chatgptapplication.database.table.Chat
import com.example.chatgptapplication.model.Completion
import com.example.chatgptapplication.model.Response
import com.example.chatgptapplication.repositories.ChatGDPRepository
import com.example.chatgptapplication.repositories.ChatRepository
import com.example.chatgptapplication.repositories.SharedPreferencesRepository
import java.util.*

class ChatViewModel(
    private val chatGDPRepository: ChatGDPRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {
    lateinit var apiKey: String
        private set
    private var chatId: String? = null
    lateinit var expertName: String

    fun initialize(context: Context) {
        setChatId()
        fetchApiKey(context)
    }
    /**
     * chatIdを生成
     */
    private fun setChatId() {
        chatId = UUID.randomUUID().toString()
    }

    /**
     * chatIdをリセット
     */
    fun resetChatId() {
        chatId = null
    }

    /**
     * Api keyをSharedPreferencesから取得
     * @return ApiKey 存在しない場合は空の文字列
     */
    fun fetchApiKey(context: Context) {
        apiKey = sharedPreferencesRepository.getApiKey(context)
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @param prompt
     * @return Response / null(取得失敗)
     */
    fun generateText(prompt: String): Response? {
        return chatGDPRepository.generateText(prompt, apiKey)
    }

    /**
     * Chatテーブルにデータを保存
     * @param prompt
     * @param response
     */
    suspend fun insert(prompt: String, response: Response) {
        if (chatId.isNullOrBlank()) {
            setChatId()
        }
        chatRepository.insert(Chat(
            chat_id = chatId!!,
            prompt = prompt,
            response = response.text,
            created_at = response.created_at
        ))
    }
}