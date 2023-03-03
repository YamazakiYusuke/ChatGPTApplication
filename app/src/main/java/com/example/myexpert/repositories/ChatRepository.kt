package com.example.myexpert.repositories

import com.example.myexpert.MainApplication
import com.example.myexpert.database.table.Chat

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
     * @return List<Chat>
     */
    suspend fun getConversation(chatId: String, limit: Int): List<Chat> {
        return chatDao.getChatsByChatIdForContext(chatId, limit)
    }
}