package jp.yusuke.myexpert.repositories

import jp.yusuke.myexpert.database.table.Chat

class ChatRepository {
    private val chatDao = jp.yusuke.myexpert.MainApplication.database.chatDao()

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
     * chatデータを取得
     * @param chatId
     * @return List<Chat>
     */
    suspend fun getChatList(chatId: String): List<Chat> {
        return chatDao.getChatsByChatId(chatId)
    }

    /**
     * roleがsystem以外chatデータを取得
     * @param chatId
     * @return List<Chat>
     */
    suspend fun getChatListWithoutSystemRole(chatId: String): List<Chat> {
        return chatDao.getChatsByChatIdWithoutSystemRole(chatId)
    }
}