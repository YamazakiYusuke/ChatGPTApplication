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
    fun insert(chat: Chat)

    /**
     * Chatテーブルからchat_idに一致するデータをcreated_atが古い順に取得
     */
    @Query("SELECT * FROM chat WHERE chat_id = :chatId ORDER BY created_at")
    suspend fun getChatsByChatId(chatId: String): List<Chat>

    /**
     * Chatテーブルからchat_idに一致するデータをcreated_atが新しい順に3件取得
     */
    @Query("SELECT * FROM chat WHERE chat_id = :chatId ORDER BY created_at desc LIMIT :limit")
    suspend fun getChatsByChatIdForContext(chatId: String, limit: Int): List<Chat>
}