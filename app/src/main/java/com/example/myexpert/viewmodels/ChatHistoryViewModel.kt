package com.example.myexpert.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myexpert.database.table.ChatThread
import com.example.myexpert.repositories.ChatThreadRepository

class ChatHistoryViewModel(
    private val chatThreadRepository: ChatThreadRepository
) : ViewModel() {
    suspend fun getChatHistory(): List<ChatThread> {
        return chatThreadRepository.getChatThread()
    }
}