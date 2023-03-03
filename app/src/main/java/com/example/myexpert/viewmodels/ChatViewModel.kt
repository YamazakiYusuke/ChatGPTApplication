package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpert.R
import com.example.myexpert.database.table.Chat
import com.example.myexpert.database.table.ChatThread
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
    private var _chatId: String? = null
    private val chatId get() = _chatId!!
    var expertNameId: Int = R.string.empty
    lateinit var expertName: String
    var expertImageId: Int = 0

    fun initialize(context: Context, chatId: String?) {
        setChatId(chatId)
        fetchApiKey(context)
    }
    /**
     * ChatThreadテーブルを登録
     */
    suspend fun insertChatThread(prompt: String) {
        chatThreadRepository.insert(
            chatId = chatId, expertImageId = expertImageId, expertNameId = expertNameId, prompt = prompt
        )
    }
    /**
     * chatIdを生成
     */
    private fun setChatId(chatId: String?) {
        if (chatId.isNullOrBlank()) {
            this._chatId = UUID.randomUUID().toString()
        } else {
            this._chatId = chatId
        }

    }

    /**
     * chatIdをリセット
     */
    fun resetChatId() {
        _chatId = null
    }

    /**
     * Api keyをSharedPreferencesから取得
     * @return ApiKey 存在しない場合は空の文字列
     */
    private fun fetchApiKey(context: Context) {
        apiKey = sharedPreferencesRepository.getApiKey(context)
    }

    /**
     * 文字列に変換したChat会話履歴を取得
     * @return 会話文脈
     */
    private suspend fun getChatContext(): String {
        var context = ""
        val conversations = chatRepository.getConversation(chatId, 1)
        conversations.forEachIndexed { index, chat ->
            context += "Context conversation $index => Q: ${chat.prompt} / A: ${chat.response}\n"
        }
        return context
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @param prompt
     * @return Response / null(取得失敗)
     */
    suspend fun generateText(prompt: String): Response? {
        return chatGDPRepository.generateText(makePrompt(prompt), getChatContext(), apiKey)
    }

    /**
     * Chat履歴を100件まで取得
     * @return List<Chat>
     */
    suspend fun getChatHistory(): List<Chat> {
        return chatRepository.getConversation(chatId, 100)
    }

    /**
     * Chatテーブルにデータを保存
     * @param prompt
     * @param response
     */
    suspend fun insert(prompt: String, response: Response) {
        chatRepository.insert(Chat(
            chat_id = chatId,
            prompt = prompt,
            response = response.text,
            created_at = response.created_at
        ))
    }

    private fun makePrompt(prompt: String): String {
        return "$expertName として以下の質問に答えてください。\n $prompt"
    }
}