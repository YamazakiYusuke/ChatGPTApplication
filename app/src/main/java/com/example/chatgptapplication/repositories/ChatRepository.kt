package com.example.chatgptapplication.repositories

import com.example.chatgptapplication.MainApplication
import com.example.chatgptapplication.database.table.Chat

class ChatRepository {
    private val chatDao = MainApplication.database.chatDao()

    /**
     * Chatテーブルにデータを保存
     */
    suspend fun insert(chat: Chat) {
        chatDao.insert(chat)
    }

    /**
     * 会話の文脈を取得
     * @param chatId
     * @param limit
     * @return String
     */
    suspend fun getConversationContext(chatId: String, limit: Int): String {
        var context = ""
        chatDao.getChatsByChatIdForContext(chatId, limit).forEachIndexed { index, chat ->
            context += "Context conversation $index => Q: ${chat.prompt} / A: ${chat.response}\n"
        }
        return context
    }
}