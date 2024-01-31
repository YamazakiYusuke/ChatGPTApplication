package jp.yusuke.myexpert.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.yusuke.myexpert.database.table.Chat

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
     * Chatテーブルからroleがsystem以外のchat_idに一致するデータをcreated_atが古い順に取得
     */
    @Query("SELECT * FROM chat WHERE chat_id = :chatId AND role != 'system' ORDER BY created_at")
    suspend fun getChatsByChatIdWithoutSystemRole(chatId: String): List<Chat>
}