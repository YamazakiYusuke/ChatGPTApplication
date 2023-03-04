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
     * chatIdに一致するChatテーブルのデータを削除
     */
    suspend fun deleteChat(chatId: String) {
        chatDao.delete(chatId)
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