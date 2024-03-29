package jp.yusuke.myexpert.repositories

import jp.yusuke.myexpert.database.table.ChatThread

class ChatThreadRepository {
    private val chatThreadDao = jp.yusuke.myexpert.MainApplication.database.chatThreadDao()

    /**
     * ChatThreadテーブルにデータを保存
     */
    suspend fun insert(chatId: String, expertImageId: Int, expertNameId: Int, prompt: String) {
        chatThreadDao.insert(ChatThread(
            chat_id = chatId,
            expert_image_id = expertImageId,
            expert_name_id = expertNameId,
            init_question = prompt
        ))
    }

    /**
     * ChatThreadテーブルからデータを取得
     */
    suspend fun getChatThread(): List<ChatThread> {
        return chatThreadDao.getChatThreadList()
    }

    /**
     * ChatThreadテーブルからデータを削除
     */
    suspend fun deleteChatThread(chatThread: ChatThread) {
        chatThreadDao.delete(chatThread)
    }
}