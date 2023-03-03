package com.example.myexpert.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myexpert.database.table.ChatThread

@Dao
interface ChatThreadDao {
    /**
     * ChatThreadテーブルにデータを保存
     */
    @Insert
    fun insert(chatThread: ChatThread)

    /**
     * それぞれのChat_idの一番新しいレコードを、新しい物順に取得
     */
    @Query("SELECT * FROM chat_thread ORDER BY created_at desc")
    suspend fun getChatThreadList(): List<ChatThread>
}