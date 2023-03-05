package com.example.myexpert.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myexpert.database.table.Chat

@Dao
interface ChatDao {

    /**
     * Chatテーブルにデータを保存
     */
    @Insert
    suspend fun insert(chat: Chat)

    /**
     * chatIdに一致するChatテーブルのデータを削除
     */
    @Query("DELETE FROM chat WHERE chat_id = :chatId")
    suspend fun delete(chatId: String)

    /**
     * Chatテーブルからchat_idに一致するデータをcreated_atが古い順に取得
     */
    @Query("SELECT * FROM chat WHERE chat_id = :chatId ORDER BY created_at")
    suspend fun getChatsByChatId(chatId: String): List<Chat>

    /**
     * Chatテーブルからchat_idに一致するデータをcreated_atが新しい順に取得
     */
    @Query("SELECT * FROM chat WHERE chat_id = :chatId ORDER BY created_at LIMIT :limit")
    suspend fun getChatsByChatIdForContext(chatId: String, limit: Int): List<Chat>
}