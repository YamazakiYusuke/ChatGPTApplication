package com.example.myexpert.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myexpert.R
import com.example.myexpert.database.table.Chat
import com.example.myexpert.models.Choice
import com.example.myexpert.models.Message
import com.example.myexpert.repositories.ChatGPTRepository
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import com.example.myexpert.repositories.SharedPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(
    private val chatGDPRepository: ChatGPTRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val chatRepository: ChatRepository,
    private val chatThreadRepository: ChatThreadRepository
) : ViewModel() {
    // APIKey
    private lateinit var apiKey: String
    // chatId
    private var _chatId: String? = null
    private val chatId get() = _chatId!!
    // 専門家名
    var expertNameId: Int = R.string.empty
    lateinit var expertName: String
    // 専門家画像
    var expertImageId: Int = 0

    fun initialize(context: Context, chatId: String?) {
        setChatId(chatId)
        fetchApiKey(context)
        setSystem(context)
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
     * アシスタンの演じる役割を指定
     */
    private fun setSystem(context: Context) {
        val userAge = sharedPreferencesRepository.getUserAge(context)

        viewModelScope.launch(Dispatchers.IO) {
            if (userAge < 6) {
                // 5歳以下
                insert(
                    role = "system",
                    content = "あなたは、$expertName です。5才児でも理解できるよう丁寧に説明してください。"
                )
            } else if(userAge < 13){
                // 小学生
                insert(
                    role = "system",
                    content = "あなたは、$expertName です。小学生でも理解できるよう丁寧に説明してください。"
                )
            } else if(userAge < 18){
                // 高校生
                insert(
                    role = "system",
                    content = "あなたは、$expertName です。高校生でも理解できるよう丁寧に説明してください。"
                )
            } else {
                insert(
                    role = "system",
                    content = "あなたは、$expertName です。"
                )
            }
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
     * 会話履歴を取得
     * @return 会話文脈
     */
    private suspend fun getMessages(): List<Message> {
        val messages: MutableList<Message> = mutableListOf()
        val conversations = chatRepository.getChatList(chatId)
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
     * Chat履歴を取得
     * @return List<Chat>
     */
    suspend fun getChatHistory(): List<Chat> {
        return chatRepository.getChatList(chatId)
    }

    /**
     * Roleがsystem以外のChat履歴を取得
     * @return List<Chat>
     */
    suspend fun getChatHistoryWithoutSystemRole(): List<Chat> {
        return chatRepository.getChatListWithoutSystemRole(chatId)
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