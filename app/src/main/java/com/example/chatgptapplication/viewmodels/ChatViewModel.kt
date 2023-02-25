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
    private var chatId: String? = null

    /**
     * chatIdを生成
     */
    fun setChatId() {
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
    fun getApiKey(context: Context): String {
        return sharedPreferencesRepository.getApiKey(context)
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @param prompt
     * @param apiKey
     * @return Response / null(取得失敗)
     */
    fun generateText(prompt: String, apiKey: String): Response? {
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