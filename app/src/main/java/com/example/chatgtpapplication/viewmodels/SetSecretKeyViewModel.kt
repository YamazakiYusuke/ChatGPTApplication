package com.example.chatgtpapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.chatgtpapplication.repositories.ChatGDPRepository
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository

class SetSecretKeyViewModel(
    private val chatGDPRepository: ChatGDPRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun checkAPIKey(apiKey: String): Boolean {
        return chatGDPRepository.checkAPIKey(apiKey)
    }

    fun setApiKey(apiKey: String, context: Context) {
        sharedPreferencesRepository.setApiKey(apiKey, context)
    }
}