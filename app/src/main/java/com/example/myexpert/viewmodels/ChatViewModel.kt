package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myexpert.database.table.Chat
import com.example.myexpert.model.Response
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
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
    private fun fetchApiKey(context: Context) {
        apiKey = sharedPreferencesRepository.getApiKey(context)
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @param prompt
     * @return Response / null(取得失敗)
     */
    suspend fun generateText(prompt: String): Response? {
        val conversationContext = chatRepository.getConversationContext(chatId!!, 1)
        return chatGDPRepository.generateText(makePrompt(prompt), conversationContext, apiKey)
    }

    /**
     * Chatテーブルにデータを保存
     * @param prompt
     * @param response
     */
    suspend fun insert(prompt: String, response: Response) {
        chatRepository.insert(Chat(
            chat_id = chatId!!,
            prompt = prompt,
            response = response.text,
            created_at = response.created_at
        ))
    }

    private fun makePrompt(prompt: String): String {
        return "$expertName として以下の質問に答えてください。\n $prompt"
    }
}