package com.example.myexpert.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myexpert.database.table.Chat
import com.example.myexpert.repositories.ChatRepository

class ChatHistoryViewModel(
    val chatRepository: ChatRepository
) : ViewModel() {
    suspend fun getChatHistory(): List<Chat> {
        return chatRepository.getChatHistory()
    }
}