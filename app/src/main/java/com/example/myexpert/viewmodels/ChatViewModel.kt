package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpert.R
import com.example.myexpert.database.table.Chat
import com.example.myexpert.model.Response
import com.example.myexpert.repositories.ChatGDPRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(
    private val chatGDPRepository: ChatGDPRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val chatRepository: ChatRepository,
    private val chatThreadRepository: ChatThreadRepository
) : ViewModel() {
    lateinit var apiKey: String
        private set
    private var chatId: String? = null
    var expertNameId: Int = R.string.empty
    lateinit var expertName: String
    var expertImageId: Int = 0

    fun initialize(context: Context) {
        setChatId()
        fetchApiKey(context)
    }
    /**
     * ChatThreadテーブルを登録
     */
    suspend fun insertChatThread(prompt: String) {
        chatThreadRepository.insert(
            chatId = chatId!!, expertImageId = expertImageId, expertNameId = expertNameId, prompt = prompt
        )
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