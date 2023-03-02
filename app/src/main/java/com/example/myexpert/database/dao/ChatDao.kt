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

    /**
     * それぞれのChat_idの一番新しいレコードを、新しい物順に取得
     */
    @Query("""
            SELECT * FROM chat t1
            WHERE t1.created_at = (
                SELECT MAX(created_at) FROM chat t2 WHERE t2.chat_id = t1.chat_id
            )
            ORDER BY created_at desc
            """)
    suspend fun getChatHistory(): List<Chat>
}