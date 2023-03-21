package com.example.myexpert.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myexpert.database.table.ChatThread
import com.example.myexpert.repositories.ChatRepository
import com.example.myexpert.repositories.ChatThreadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatHistoryViewModel @Inject constructor(
    private val chatThreadRepository: ChatThreadRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {
    /**
     * ChatThreadテーブルからデータを取得
     */
    suspend fun getChatHistory(): List<ChatThread> {
        return chatThreadRepository.getChatThread()
    }

    /**
     * ChatThread、ChatテーブルからChatIdに一致するレコードを削除
     */
    suspend fun deleteChat(chatThread: ChatThread) {
        chatThreadRepository.deleteChatThread(chatThread)
        chatRepository.deleteChat(chatThread.chat_id)
    }
}