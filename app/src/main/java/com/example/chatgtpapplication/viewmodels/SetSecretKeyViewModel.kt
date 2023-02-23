package com.example.chatgtpapplication.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.chatgtpapplication.repositories.ChatGDPRepository
import com.example.chatgtpapplication.repositories.SharedPreferencesRepository

class SetSecretKeyViewModel(
    private val chatGDPRepository: ChatGDPRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    /**
     * ApiKeyが有効化を確認
     * @param apiKey
     * @return true(有効) / false(無効)
     */
    fun checkAPIKey(apiKey: String): Boolean {
        return chatGDPRepository.checkAPIKey(apiKey)
    }

    /**
     * Api keyをSharedPreferencesに保存
     * @param apiKey
     * @param context
     */
    fun setApiKey(apiKey: String, context: Context) {
        sharedPreferencesRepository.setApiKey(apiKey, context)
    }
}