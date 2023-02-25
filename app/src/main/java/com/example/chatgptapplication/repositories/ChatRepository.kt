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
     */
    suspend fun getChatsByChatIdForContext(chatId: String): List<Chat> {
        return chatDao.getChatsByChatIdForContext(chatId)
    }
}