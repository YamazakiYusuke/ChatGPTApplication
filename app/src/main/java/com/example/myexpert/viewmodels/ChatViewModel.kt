package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myexpert.R
import com.example.myexpert.database.table.Chat
import com.example.myexpert.models.Choice
import com.example.myexpert.models.Message
import com.example.myexpert.repositories.ChatGPTRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import java.util.*

class ChatViewModel(
    private val chatGDPRepository: ChatGPTRepository,
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
     * @param newContent
     * @return 会話文脈
     */
    private suspend fun getMessages(): List<Message> {
        val messages: MutableList<Message> = mutableListOf()
        val conversations = chatRepository.getConversation(chatId, 100)
        conversations.forEachIndexed { _, chat ->
            messages.add(Message(chat.role, chat.content))
        }
        return messages
    }

    /**
     * ChatGPTにテキスト生成をリクエスト
     * @return Choice / null(取得失敗)
     */
    suspend fun generateText(): Choice? {
        return chatGDPRepository.generateText(getMessages(), apiKey)
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
     * @param role
     * @param content
     */
    suspend fun insert(role: String, content: String) {
        chatRepository.insert(Chat(
            chat_id = chatId,
            role = role,
            content = content
        ))
    }
}